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
package com.roncoo.pay.app.notify.core;

import com.roncoo.pay.notify.entity.RpNotifyRecord;
import com.roncoo.pay.notify.entity.RpNotifyRecordLog;
import com.roncoo.pay.notify.service.RpNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <b>功能说明:
 * </b>
 * @author  Peter
 * <a href="http://www.roncoo.com">龙果学院(www.roncoo.com)</a>
 */
@Service("notifyPersist")
public class NotifyPersist {

    @Autowired
    private RpNotifyService rpNotifyService;

    /**
     * 创建商户通知记录.<br/>
     *
     * @param notifyRecord
     * @return
     */
    public long saveNotifyRecord(RpNotifyRecord notifyRecord) {
        return rpNotifyService.createNotifyRecord(notifyRecord);
    }

    /**
     * 更新商户通知记录.<br/>
     *
     * @param id
     * @param notifyTimes
     *            通知次数.<br/>
     * @param status
     *            通知状态.<br/>
     * @return 更新结果
     */
    public  void updateNotifyRord(String id, int notifyTimes, String status) {
        RpNotifyRecord notifyRecord = rpNotifyService.getNotifyRecordById(id);
        notifyRecord.setNotifyTimes(notifyTimes);
        notifyRecord.setStatus(status);
        notifyRecord.setLastNotifyTime(new Date());
        rpNotifyService.updateNotifyRecord(notifyRecord);
    }

    /**
     * 创建商户通知日志记录.<br/>
     *
     * @param notifyId
     *            通知记录ID.<br/>
     * @param merchantNo
     *            商户编号.<br/>
     * @param merchantOrderNo
     *            商户订单号.<br/>
     * @param request
     *            请求信息.<br/>
     * @param response
     *            返回信息.<br/>
     * @param httpStatus
     *            通知状态(HTTP状态).<br/>
     * @return 创建结果
     */
    public long saveNotifyRecordLogs(String notifyId, String merchantNo, String merchantOrderNo, String request, String response,
                                            int httpStatus) {
        RpNotifyRecordLog notifyRecordLog = new RpNotifyRecordLog();
        notifyRecordLog.setHttpStatus(httpStatus);
        notifyRecordLog.setMerchantNo(merchantNo);
        notifyRecordLog.setMerchantOrderNo(merchantOrderNo);
        notifyRecordLog.setNotifyId(notifyId);
        notifyRecordLog.setRequest(request);
        notifyRecordLog.setResponse(response);
        notifyRecordLog.setCreateTime(new Date());
        notifyRecordLog.setEditTime(new Date());
        return rpNotifyService.createNotifyRecordLog(notifyRecordLog);
    }

}
