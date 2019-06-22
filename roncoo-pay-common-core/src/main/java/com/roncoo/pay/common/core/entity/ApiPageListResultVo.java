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
package com.roncoo.pay.common.core.entity;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

/**
 * API请求,返回分页数据时,统一实体类,将返回的数据统一封装到该实体中,返回给客户端
 * 龙果学院：www.roncoo.com
 * @author zenghao
 */
public class ApiPageListResultVo {

    /**
     * 返回码
     */
    private int code;

    /**
     *  返回描述
     */
    private String msg = "";

    /**
     *  返回分页数据,默认为0页0条
     */
    private PageListVO data = new PageListVO(0,0,0,new ArrayList<rpObject>());

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(PageListVO data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public PageListVO getData() {
        return data;
    }



    public static  void main(String [] args ){

        ApiPageListResultVo apiPageListResultVo = new ApiPageListResultVo();
        apiPageListResultVo.setCode(-1);
        apiPageListResultVo.setMsg("测试数据");

        PageListVO pageListVO = new PageListVO(0,2,33,new ArrayList<Object>());

        apiPageListResultVo.setData(pageListVO);

        System.out.println(JSONObject.toJSONString(apiPageListResultVo));
    }

}
