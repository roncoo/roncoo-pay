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
import java.util.Date;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Service;

import com.roncoo.pay.reconciliation.fileDown.service.FileDown;
import com.roncoo.pay.reconciliation.fileDown.service.ReconciliationFactory;
import com.roncoo.pay.reconciliation.utils.ReconciliationConfigUtil;

/**
 * 文件下载factory
 *
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
@Service("reconciliationFactory")
public class ReconciliationFactoryImpl implements ReconciliationFactory, BeanFactoryAware {

	private BeanFactory beanFactory;

	/**
	 * 去Spring容器中根据beanName获取对象（也可以直接根据名字创建实例，可以参考后面流程中的parser）
	 * 
	 * @param payInterface
	 * @return
	 */
	public Object getService(String payInterface) {
		return beanFactory.getBean(payInterface);
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	/**
	 * 账单下载
	 * 
	 * @param payInterface
	 *            支付渠道
	 * 
	 * @param billDate
	 *            账单日
	 */
	public File fileDown(String payInterface, Date billDate) throws Exception {
		// 找到具体的FileDown实现，做向上转型
		FileDown fileDown = (FileDown) this.getService(payInterface);
		// 加载配置文件，获取下载的对账文件保存路径
		String dir = ReconciliationConfigUtil.readConfig("dir") + payInterface.toLowerCase();
		return fileDown.fileDown(billDate, dir);
	}

}
