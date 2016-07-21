package com.roncoo.pay.reconciliation.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * 
 * @类功能说明： 加载对账配置熟悉文件工具类.
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：广州领课网络科技有限公司（龙果学院:www.roncoo.com）
 * @作者：Along.shen
 * @创建时间：2016年6月16日,下午4:25:35.
 * @版本：V1.0
 *
 */
public class ReconciliationConfigUtil {

	private static final Log LOG = LogFactory.getLog(ReconciliationConfigUtil.class);

	/**
	 * 通过静态代码块读取上传文件的验证格式配置文件,静态代码块只执行一次(单例)
	 */
	private static Properties properties = new Properties();

	private ReconciliationConfigUtil() {

	}

	// 通过类装载器装载进来
	static {
		try {
			// 从类路径下读取属性文件
			properties.load(ReconciliationConfigUtil.class.getClassLoader().getResourceAsStream("reconciliation_config.properties"));
		} catch (IOException e) {
			LOG.error(e);
		}
	}

	/**
	 * 函数功能说明 ：读取配置项 Administrator 2012-12-14 修改者名字 ： 修改日期 ： 修改内容 ：
	 *
	 * @参数：
	 * @return void
	 * @throws
	 */
	public static String readConfig(String key) {
		return (String) properties.get(key);
	}
}
