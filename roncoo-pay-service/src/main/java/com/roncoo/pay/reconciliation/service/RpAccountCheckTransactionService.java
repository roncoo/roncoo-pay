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
package com.roncoo.pay.reconciliation.service;

import java.util.List;

import com.roncoo.pay.reconciliation.entity.RpAccountCheckBatch;
import com.roncoo.pay.reconciliation.entity.RpAccountCheckMistake;
import com.roncoo.pay.reconciliation.entity.RpAccountCheckMistakeScratchPool;

/**
 * 对账数据事务一致性service.
 *
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
public interface RpAccountCheckTransactionService {

	/**
	 * 保存
	 */
	void saveDatasaveDate(RpAccountCheckBatch batch, List<RpAccountCheckMistake> mistakeList, List<RpAccountCheckMistakeScratchPool> insertScreatchRecordList, List<RpAccountCheckMistakeScratchPool> removeScreatchRecordList);

	/**
	 * 
	 * @param list
	 * @param mistakeList
	 */
	void removeDateFromPool(List<RpAccountCheckMistakeScratchPool> list, List<RpAccountCheckMistake> mistakeList);

	/**
	 * 差错处理
	 * 
	 * @param id
	 *            差错记录id
	 * @param handleType
	 *            差错处理方式
	 * @param handleRemark
	 *            差错备注
	 */
	void handle(String id, String handleType, String handleRemark);

}