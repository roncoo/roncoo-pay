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
package com.roncoo.pay.common.core.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 分页组件.
 * @company：广州领课网络科技有限公司（龙果学院 www.roncoo.com）.
 * @author along
 */
public class PageBean<T> implements Serializable {
    /**
   * 
   */
    private static final long serialVersionUID = 8470697978259453214L;

    // 指定的或是页面参数
    private int currentPage; // 当前页
    private int numPerPage; // 每页显示多少条

    // 查询数据库
    private int totalCount; // 总记录数
    private List<T> recordList =new  ArrayList<T>(0); // 本页的数据列表

    // 计算
    private int totalPage; // 总页数
    private int beginPageIndex; // 页码列表的开始索引（包含）
    private int endPageIndex; // 页码列表的结束索引（包含）

    private Map<String, Object> countResultMap; // 当前分页条件下的统计结果

    public PageBean() {
    }

    /**
     * 计算总页数 .
     * 
     * @param totalCount
     *            总记录数.
     * @param numPerPage
     *            每页记录数.
     * @return totalPage 总页数.
     */
    public static int countTotalPage(int totalCount, int numPerPage) {
        if (totalCount % numPerPage == 0) {
            // 刚好整除
            return totalCount / numPerPage;
        } else {
            // 不能整除则总页数为：商 + 1
            return totalCount / numPerPage + 1;
        }
    }

    /**
     * 校验当前页数currentPage.<br/>
     * 1、先根据总记录数totalCount和每页记录数numPerPage，计算出总页数totalPage.<br/>
     * 2、判断页面提交过来的当前页数currentPage是否大于总页数totalPage，大于则返回totalPage.<br/>
     * 3、判断currentPage是否小于1，小于则返回1.<br/>
     * 4、其它则直接返回currentPage .
     * 
     * @param totalCount
     *            要分页的总记录数 .
     * @param numPerPage
     *            每页记录数大小 .
     * @param currentPage
     *            输入的当前页数 .
     * @return currentPage .
     */
    public static int checkCurrentPage(int totalCount, int numPerPage,
            int currentPage) {
        int totalPage = PageBean.countTotalPage(totalCount, numPerPage); // 最大页数
        if (currentPage > totalPage) {
            // 如果页面提交过来的页数大于总页数，则将当前页设为总页数
            // 此时要求totalPage要大于获等于1
            if (totalPage < 1) {
                return 1;
            }
            return totalPage;
        } else if (currentPage < 1) {
            return 1; // 当前页不能小于1（避免页面输入不正确值）
        } else {
            return currentPage;
        }
    }

    /**
     * 校验页面输入的每页记录数numPerPage是否合法 .<br/>
     * 1、当页面输入的每页记录数numPerPage大于允许的最大每页记录数MAX_PAGE_SIZE时，返回MAX_PAGE_SIZE.
     * 2、如果numPerPage小于1，则返回默认的每页记录数DEFAULT_PAGE_SIZE.
     * 
     * @param numPerPage
     *            页面输入的每页记录数 .
     * @return checkNumPerPage .
     */
    public static int checkNumPerPage(int numPerPage) {
        if (numPerPage > PageParam.MAX_PAGE_SIZE) {
            return PageParam.MAX_PAGE_SIZE;
        } else if (numPerPage < 1) {
            return PageParam.DEFAULT_NUM_PER_PAGE;
        } else {
            return numPerPage;
        }
    }

    /**
     * 只接受前4个必要的属性，会自动的计算出其他3个属生的值
     * 
     * @param currentPage
     * @param numPerPage
     * @param totalCount
     * @param recordList
     */
    public PageBean(int currentPage, int numPerPage, int totalCount,
            List<T> recordList) {
        this.currentPage = currentPage;
        this.numPerPage = numPerPage;
        this.totalCount = totalCount;
        this.recordList = recordList;

        // 计算总页码
        totalPage = (totalCount + numPerPage - 1) / numPerPage;

        // 计算 beginPageIndex 和 endPageIndex
        if (totalPage <= 10) {
            // 如果总页数不多于10页，则全部显示
            beginPageIndex = 1;
            endPageIndex = totalPage;
        } else {
            // 如果总页数多于10页，则显示当前页附近的共10个页码
            // 当前页附近的共10个页码（前4个 + 当前页 + 后5个）
            beginPageIndex = currentPage - 4;
            endPageIndex = currentPage + 5;
            // 当前面的页码不足4个时，则显示前10个页码
            if (beginPageIndex < 1) {
                beginPageIndex = 1;
                endPageIndex = 10;
            }
            // 当后面的页码不足5个时，则显示后10个页码
            if (endPageIndex > totalPage) {
                endPageIndex = totalPage;
                beginPageIndex = totalPage - 10 + 1;
            }
        }
    }

    /**
     * 只接受前5个必要的属性，会自动的计算出其他3个属生的值
     * 
     * @param currentPage
     * @param numPerPage
     * @param totalCount
     * @param recordList
     */
    public PageBean(int currentPage, int numPerPage, int totalCount,
            List<T> recordList, Map<String, Object> countResultMap) {
        this.currentPage = currentPage;
        this.numPerPage = numPerPage;
        this.totalCount = totalCount;
        this.recordList = recordList;
        this.countResultMap = countResultMap;

        // 计算总页码
        totalPage = (totalCount + numPerPage - 1) / numPerPage;

        // 计算 beginPageIndex 和 endPageIndex
        if (totalPage <= 10) {
            // 如果总页数不多于10页，则全部显示
            beginPageIndex = 1;
            endPageIndex = totalPage;
        } else {
            // 如果总页数多于10页，则显示当前页附近的共10个页码
            // 当前页附近的共10个页码（前4个 + 当前页 + 后5个）
            beginPageIndex = currentPage - 4;
            endPageIndex = currentPage + 5;
            // 当前面的页码不足4个时，则显示前10个页码
            if (beginPageIndex < 1) {
                beginPageIndex = 1;
                endPageIndex = 10;
            }
            // 当后面的页码不足5个时，则显示后10个页码
            if (endPageIndex > totalPage) {
                endPageIndex = totalPage;
                beginPageIndex = totalPage - 10 + 1;
            }
        }
    }

    public List<T> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<T> recordList) {
        this.recordList = recordList;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getBeginPageIndex() {
        return beginPageIndex;
    }

    public void setBeginPageIndex(int beginPageIndex) {
        this.beginPageIndex = beginPageIndex;
    }

    public int getEndPageIndex() {
        return endPageIndex;
    }

    public void setEndPageIndex(int endPageIndex) {
        this.endPageIndex = endPageIndex;
    }

    public Map<String, Object> getCountResultMap() {
        return countResultMap;
    }

    public void setCountResultMap(Map<String, Object> countResultMap) {
        this.countResultMap = countResultMap;
    }

}
