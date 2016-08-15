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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.druid.util.StringUtils;
import com.roncoo.pay.reconciliation.fileDown.service.FileDown;
import com.roncoo.pay.reconciliation.utils.FileUtils;
import com.roncoo.pay.reconciliation.utils.SignHelper;
import com.roncoo.pay.reconciliation.utils.WeiXinBaseUtils;
import com.roncoo.pay.reconciliation.utils.https.HttpClientUtil;
import com.roncoo.pay.reconciliation.utils.https.HttpResponse;
import com.roncoo.pay.trade.utils.WeixinConfigUtil;

/**
 * 微信文件下载类
 *
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
public class WinXinFileDown implements FileDown {

	private static final Log LOG = LogFactory.getLog(WinXinFileDown.class);

	/*** 配置全部放入weixinpay_config.properties配置文件中/ ***/
	private String url = WeixinConfigUtil.readConfig("download_bill_url");

	// 公众账号ID
	private String appid = WeixinConfigUtil.readConfig("appId");;

	// 商户号
	private String mch_id = WeixinConfigUtil.readConfig("mch_id");

	// 对账单日期 格式：20140603
	private String bill_date;

	// 微信密钥
	private String appSecret = WeixinConfigUtil.readConfig("partnerKey");

	// 对账类型：
	// ALL，返回当日所有订单信息，默认值
	// SUCCESS，返回当日成功支付的订单
	// REFUND，返回当日退款订单
	private String bill_type = WeixinConfigUtil.readConfig("bill_type");

	/**
	 * 文件下载类
	 *
	 * @param billDate
	 *            账单日
	 * @param dir
	 *            账单保存路径
	 * 
	 */

	public File fileDown(Date billDate, String dir) throws IOException {
		// 时间格式转换
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		bill_date = sdf.format(billDate);
		HttpResponse response = null;
		try {
			// 生成xml文件
			String xml = this.generateXml();
			LOG.info(xml);

			response = HttpClientUtil.httpsRequest(url, "POST", xml);

			// String dir = "/home/roncoo/app/accountcheck/billfile/weixin";

			File file = new File(dir, bill_date + "_" + bill_type.toLowerCase() + ".txt");
			int index = 1;

			// 判断文件是否已经存在
			while (file.exists()) {
				file = new File(dir, bill_date + "_" + bill_type.toLowerCase() + index + ".txt");
				index++;
			}
			return FileUtils.saveFile(response, file);

		} catch (IOException e) {
			throw new IOException("下载微信账单失败", e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				LOG.error("关闭下载账单的流/连接失败", e);
			}
		}
	}

	/**
	 * 根据微信接口要求，生成xml文件
	 * 
	 * @param appId
	 *            必填
	 * @param mchId
	 *            必填
	 * @param billDate
	 *            必填, 下载对账单的日期(最小单位天)
	 * @param billType
	 *            下载单类型
	 * @param appSecret
	 *            必填, 供签名使用
	 * @return
	 */
	public String generateXml() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("appid", appid);
		params.put("mch_id", mch_id);
		params.put("bill_date", bill_date);
		params.put("bill_type", bill_type);
		// 随机字符串，不长于32，调用随机数函数生成，将得到的值转换为字符串
		params.put("nonce_str", WeiXinBaseUtils.createNoncestr());

		// 过滤空值
		for (Iterator<Entry<String, String>> it = params.entrySet().iterator(); it.hasNext();) {
			Entry<String, String> entry = it.next();
			if (StringUtils.isEmpty(entry.getValue())) {
				it.remove();
			}
		}

		String sign = SignHelper.getSign(params, appSecret);
		params.put("sign", sign.toUpperCase());
		return WeiXinBaseUtils.arrayToXml(params);
	}

}
