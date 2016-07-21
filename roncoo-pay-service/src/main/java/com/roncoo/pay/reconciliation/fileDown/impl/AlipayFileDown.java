/*
 * Copyright 2015-2102 RonCoo(http://www.roncoo.com) Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.roncoo.pay.reconciliation.fileDown.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.roncoo.pay.common.core.utils.DateUtils;
import com.roncoo.pay.reconciliation.fileDown.service.FileDown;
import com.roncoo.pay.reconciliation.utils.alipay.AlipaySubmit;
import com.roncoo.pay.reconciliation.utils.alipay.httpClient.HttpProtocolHandler;
import com.roncoo.pay.reconciliation.utils.alipay.httpClient.HttpRequest;
import com.roncoo.pay.reconciliation.utils.alipay.httpClient.HttpResponse;
import com.roncoo.pay.reconciliation.utils.alipay.httpClient.HttpResultType;
import com.roncoo.pay.trade.utils.AlipayConfigUtil;

/**
 * 支付宝账单下载.
 *
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
public class AlipayFileDown implements FileDown {
	private static final Log LOG = LogFactory.getLog(AlipayFileDown.class);
	SimpleDateFormat timestampSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat billDateSDF = new SimpleDateFormat("yyyy-MM-dd");

	/*** 配置全部放入alipay_config.properties配置文件中/ ***/
	// #合作身份者ID，签约账号
	private String partner = AlipayConfigUtil.readConfig("partner");

	// 支付宝网关
	private String url = AlipayConfigUtil.readConfig("alipay_gateway_new");

	// 请求使用的编码格式
	private String charset = AlipayConfigUtil.readConfig("input_charset");

	// 账单查询开始时间：格式为：yyyy-MM-dd HH:mm:ss
	private String gmt_start_time = "";

	// 账单查询开始时间：格式为：yyyy-MM-dd HH:mm:ss
	private String gmt_end_time = "";

	// pageNo 分页查询的页号：默认是1
	private String pageNo = "1";

	/**
	 * 文件下载类
	 *
	 * @param billDate
	 *            账单日
	 * @param dir
	 *            账单保存路径
	 * 
	 */
	public File fileDown(Date fileDate, String dir) throws Exception {

		LOG.info("======开始下载支付宝对账单");
		// 格式化账单日期
		String bill_begin_date = billDateSDF.format(fileDate);
		String bill_end_date = billDateSDF.format(DateUtils.addDay(fileDate, 1));
		gmt_start_time = bill_begin_date + " 00:00:00";
		gmt_end_time = bill_end_date + " 00:00:00";

		HttpResponse response = null;

		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "account.page.query");
		sParaTemp.put("partner", partner);
		sParaTemp.put("_input_charset", charset);
		sParaTemp.put("page_no", pageNo);
		sParaTemp.put("gmt_start_time", gmt_start_time);
		sParaTemp.put("gmt_end_time", gmt_end_time);

		response = this.buildRequest(sParaTemp);
		if (response == null) {
			return null;
		}
		// 得到支付宝接口返回数据
		String stringResult = response.getStringResult();

		// 创建保存对账单的本地文件
		File file = this.createFile(bill_begin_date, stringResult, dir);

		return file;
	}

	/**
	 * 建立请求，以模拟远程HTTP的POST请求方式构造并获取支付宝的处理结果
	 * 
	 * @param sParaTemp
	 *            请求参数
	 * @return 支付宝处理结果
	 * @throws Exception
	 */
	public HttpResponse buildRequest(Map<String, String> sParaTemp) throws Exception {
		// 待请求参数数组
		Map<String, String> sPara = AlipaySubmit.buildRequestPara(sParaTemp);

		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

		HttpRequest request = new HttpRequest(HttpResultType.BYTES);
		// 设置编码集
		request.setCharset(charset);
		// 设置请求参数
		request.setParameters(AlipaySubmit.generatNameValuePair(sPara));
		// 设置请求地址
		request.setUrl(url + "_input_charset=" + charset);
		// 请求接口
		HttpResponse response = httpProtocolHandler.execute(request, "", "");
		if (response == null) {
			return null;
		}
		return response;
	}

	/**
	 * 创建账单文件
	 * 
	 * @param bill_date
	 *            账单日
	 * @param stringResult
	 *            文件内容
	 * @param dir
	 *            文件保存路径
	 * @return
	 * @throws IOException
	 */
	private File createFile(String bill_date, String stringResult, String dir) throws IOException {

		// 创建本地文件，用于存储支付宝对账文件
		// String dir = "/home/roncoo/app/accountcheck/billfile/alipay";
		File file = new File(dir, bill_date + "_" + ".xml");
		int index = 1;
		// 判断文件是否已经存在
		while (file.exists()) {
			file = new File(dir, bill_date + "_" + index + ".xml");
			index++;
		}

		// 判断父文件是否存在,不存在就创建
		if (!file.getParentFile().exists()) {
			if (!file.getParentFile().mkdirs()) {
				// 新建文件目录失败，抛异常
				throw new IOException("创建文件(父层文件夹)失败, filepath: " + file.getAbsolutePath());
			}
		}
		// 判断文件是否存在，不存在则创建
		if (!file.exists()) {
			if (!file.createNewFile()) {
				// 新建文件失败，抛异常
				throw new IOException("创建文件失败, filepath: " + file.getAbsolutePath());
			}
		}

		try {
			// 把支付宝返回数据写入文件
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(stringResult);
			fileWriter.close(); // 关闭数据流

		} catch (IOException e) {
			LOG.info("把支付宝返回的对账数据写入文件异常:" + e);
		}

		return file;
	}

}
