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
package com.roncoo.pay.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.roncoo.pay.common.core.dao.impl.BaseDaoImpl;
import com.roncoo.pay.user.dao.BuildNoDao;
import com.roncoo.pay.user.entity.SeqBuild;

/**
 *  生成编号dao实现类
 * 龙果学院：www.roncoo.com
 * @author：zenghao
 */
@Repository
public class BuildNoDaoImpl   extends BaseDaoImpl<SeqBuild> implements BuildNoDao {

    @Override
    public String getSeqNextValue(SeqBuild seqBuild) {
        return super.getSqlSession().selectOne(getStatement("getSeqNextValue"),seqBuild);
    }
}
