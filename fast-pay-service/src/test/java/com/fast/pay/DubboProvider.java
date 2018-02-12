package com.fast.pay;

import com.fast.pay.account.service.RpAccountTransactionService;
import com.fast.pay.permission.service.PmsOperatorService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fast.pay.account.service.RpAccountTransactionService;
import com.fast.pay.common.core.page.PageBean;
import com.fast.pay.common.core.page.PageParam;
import com.fast.pay.permission.service.PmsOperatorService;

/**
 * @描述: 启动Dubbo服务用的MainClass.
 * @作者: WuShuicheng .
 * @创建时间: 2013-11-5,下午9:47:55 .
 * @版本: 1.0 .
 */
public class DubboProvider {

	private static final Log log = LogFactory.getLog(DubboProvider.class);

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		try {
			ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/spring-context-service.xml");
			RpAccountTransactionService rpAccountTransactionService = (RpAccountTransactionService) ac.getBean("rpAccountTransactionService");
			PmsOperatorService operator = (PmsOperatorService) ac.getBean("pmsOperatorService");
			PageBean listPage = operator.listPage(new PageParam(), null);
			listPage.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("== DubboProvider context start error:", e);
		}
	}

}