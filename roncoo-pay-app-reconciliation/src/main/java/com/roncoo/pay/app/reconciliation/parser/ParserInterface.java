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
package com.roncoo.pay.app.reconciliation.parser;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.roncoo.pay.reconciliation.entity.RpAccountCheckBatch;
import com.roncoo.pay.reconciliation.vo.ReconciliationEntityVo;

/**
 * 定义一个解析的接口，实现着必须override接口中的parser方法.
 * 
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
public interface ParserInterface {

	/**
	 * 
	 * @param file
	 * @param billDate
	 * @param batch
	 * @return
	 * @throws IOException 
	 */
	public List<ReconciliationEntityVo> parser(File file, Date billDate, RpAccountCheckBatch batch) throws IOException;

}
