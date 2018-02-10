/*
 * Copyright 2015-2102 Fast(http://www.cloudate.net) Group.
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
package com.fast.pay.user.dao;

import com.fast.pay.common.core.dao.BaseDao;
import com.fast.pay.user.entity.RpUserPayInfo;
import com.fast.pay.user.entity.RpUserPayInfo;

/**
 * 用户第三方支付信息dao
 */
public interface RpUserPayInfoDao  extends BaseDao<RpUserPayInfo> {

    /**
     * 通过商户编号获取商户第三方支付信息
     * @param userNo
     * @param payWayCode
     * @return
     */
    public  RpUserPayInfo getByUserNo(String userNo, String payWayCode);

}