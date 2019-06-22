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

import java.util.ArrayList;
import java.util.List;

/**
 * 返回的分页实体
 * 龙果学院：www.roncoo.com
 * @author zenghao
 */
public class PageListVO {

    /** 总数量 **/
    private long total ;

    /** 页码 **/
    private int page;

    /** 每页条数 **/
    private int pageSize;

    /** 分页数据 **/
    private List pageData = new ArrayList();
    
    /**
	 * 汇总数据
	 */
	private Object summary;

	public PageListVO(long total , int page , int pageSize , List pageData){
        this.total = total;
        this.page = page;
        this.pageSize = pageSize;

        if (pageData != null){
            this.pageData = pageData;
        }

    }
	
	public PageListVO(long total , int page , int pageSize , List pageData, Object summary){
        this.total = total;
        this.page = page;
        this.pageSize = pageSize;
        this.summary = summary;

        if (pageData != null){
            this.pageData = pageData;
        }

    }
	
    public Object getSummary() {
		return summary;
	}

	public void setSummary(Object summary) {
		this.summary = summary;
	}

    public void setTotal(int total) {
        this.total = total;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setPageData(List pageData) {
        this.pageData = pageData;
    }

    public long getTotal() {
        return total;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public List getPageData() {
        return pageData;
    }
}
