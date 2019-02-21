package com.roncoo.pay.trade.service;

import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.trade.entity.RpMicroSubmitRecord;

import java.util.Map;

/**
 * 小微商户进件service接口
 *
 * @author Quanf
 */
public interface RpMicroSubmitRecordService {

    /**
     * 获取分页信息
     *
     * @param pageParam
     * @param rpMicroSubmitRecord
     * @return
     */
    PageBean listPage(PageParam pageParam, RpMicroSubmitRecord rpMicroSubmitRecord);

    /**
     * 保存
     *
     * @param rpMicroSubmitRecord
     */
    void saveData(RpMicroSubmitRecord rpMicroSubmitRecord);

    /**
     * 进件
     *
     * @param rpMicroSubmitRecord
     */
    Map<String, Object> microSubmit(RpMicroSubmitRecord rpMicroSubmitRecord);

    /**
     * 查询
     *
     * @param businessCode
     * @return
     */
    Map<String, Object> microQuery(String businessCode);

}
