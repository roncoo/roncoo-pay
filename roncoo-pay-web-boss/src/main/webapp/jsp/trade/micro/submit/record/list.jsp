<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../../../../common/taglib/taglib.jsp" %>
<form id="pagerForm" method="post" action="${baseURL }/trade/micro/submit/record/list">
    <%@include file="../../../../common/pageParameter.jsp" %>
</form>
<div class="pageHeader">
    <form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${baseURL }/trade/micro/submit/record/list" method="post">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td colspan="2">
                        业务编号：
                        <input type="text" name="businessCode" value="${rpMicroSubmitRecord.businessCode}"/>
                        &nbsp;&nbsp;
                        门店名称：
                        <input type="text" name="storeName" value="${rpMicroSubmitRecord.storeName}"/>
                        &nbsp;&nbsp;
                        联系人：
                        <input type="text" name="idCardName" value="${rpMicroSubmitRecord.idCardName}"/>
                    </td>
                    <td>
                        <div class="buttonActive">
                            <div class="buttonContent">
                                <button title="查询" type="submit">查&nbsp;询</button>
                            </div>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </form>
</div>
<div class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <li>
                <shiro:hasPermission name="trade:micro:submit:record:add">
                    <a title="微信-小微商户进件" class="add" href="${baseURL }/trade/micro/submit/record/addUI" target="navTab">
                        <span>微信-小微商户进件</span>
                    </a>
                </shiro:hasPermission>
            </li>
            <li class="line">line</li>
        </ul>
    </div>
    <table class="table" width="90%" layoutH="85" nowrapTD="false">
        <thead>
        <tr>
            <th width="20">序号</th>
            <th width="30">业务编号</th>
            <th width="250">门店名称(简称)</th>
            <th width="30">联系人(手机)</th>
            <th width="80">银行名称(卡号)</th>
            <th width="80">客服电话(类型)(费率)</th>
            <th width="50">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${pageBean.recordList}" varStatus="s">
            <tr>
                <td>${s.index + 1}</td>
                <td>${item.businessCode}</td>
                <td>${item.storeName}(${item.merchantShortname})<br>${item.storeStreet}</td>
                <td>${item.idCardName}<br>${item.contactPhone}</td>
                <td>${item.accountBank}<br>${item.accountNumber}</td>
                <td>${item.servicePhone}<br>(${item.productDesc})(${item.rate})</td>
                <td>
                    <shiro:hasPermission name="trade:micro:submit:record:query">
                        <a target="dialog" width="600" height="470" rel="input" href="${baseURL }/trade/micro/submit/record/query/${item.businessCode}"
                           style="color: blue;">查询(进行绑定)</a>
                    </shiro:hasPermission>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${pageBean.totalCount==0}">
            <tr>
                <td>暂无数据</td>
            </tr>
        </c:if>
        </tbody>
    </table>
    <%@include file="../../../../common/pageBar.jsp" %>
</div>
