<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../../common/taglib/taglib.jsp"%>
<form id="pagerForm" method="post" action="${baseURL }/trade/listPaymentRecord">
  <%@include file="../common/pageParameter.jsp" %>
</form>
<div class="pageHeader">
  <form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${baseURL }/trade/listPaymentRecord"
        method="post">
    <div class="searchBar">
      <table class="searchContent">
        <tr>
          <td>商户编号：
            <input type="text" name="merchantNo" value="${paymentOrderQueryParam.merchantNo}" />
          </td>
          <td>商户名称：
            <input type="text" name="merchantName" value="${paymentOrderQueryParam.merchantName}" />
          </td>
          <td>商户订单号：
            <input type="text" name="merchantOrderNo" value="${paymentOrderQueryParam.merchantOrderNo}" />
          </td>
          <td>状态：

            <select name="status" >
              <option value="" >请选择</option>
              <c:forEach items="${statusEnums}" var="statusVar">
                <option value="${statusVar.key}"
                        <c:if test="${paymentOrderQueryParam.status == statusVar.key}"> selected="selected"</c:if>
                        >${statusVar.value.desc}</option>
              </c:forEach>
            </select>
          </td>
        </tr>
        <tr>
          <td>
            下单开始日期:<input type="text" name="orderDateBegin" value="${paymentOrderQueryParam.orderDateBegin}" class="date textInput readonly" readonly="true">
          </td>
          <td>
            下单结束日期:<input type="text" name="orderDateEnd" value="${paymentOrderQueryParam.orderDateEnd}" class="date textInput readonly" readonly="true">
          </td>
        </tr>
        <tr>
          <td>支付方式：
            <select name="payWayName" >
              <option value="" >请选择</option>
              <c:forEach items="${payWayNameEnums}" var="payWayNameVar">
                <option value="${payWayNameVar.value.desc}"
                        <c:if test="${paymentOrderQueryParam.payWayName == payWayNameVar.value.desc}"> selected="selected"</c:if>
                        >${payWayNameVar.value.desc}</option>
              </c:forEach>
            </select>
          </td>
          <td>支付类型：
            <select name="payTypeName" >
              <option value="" >请选择</option>
              <c:forEach items="${payTypeNameEnums}" var="payTypeNameVar">
                <option value="${payTypeNameVar.value.desc}"
                        <c:if test="${paymentOrderQueryParam.payTypeName == payTypeNameVar.value.desc}"> selected="selected"</c:if>
                        >${payTypeNameVar.value.desc}</option>
              </c:forEach>
            </select>
          </td>
          <td>资金流入类型：
            <select name="fundIntoType" >
              <option value="" >请选择</option>
              <c:forEach items="${fundIntoTypeEnums}" var="fundIntoTypeVar">
                <option value="${fundIntoTypeVar.key}"
                        <c:if test="${paymentOrderQueryParam.fundIntoType == fundIntoTypeVar.key}"> selected="selected"</c:if>
                        >${fundIntoTypeVar.value.desc}</option>
              </c:forEach>
            </select>
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
<div class="pageContent" style="overflow: scroll;">
  <table class="table" width="1500" layoutH="132">
    <thead>
    <tr>
      <th width="3%">序号</th>
      <th width="10%">商家编号</th>
      <th width="10%">商家名称</th>
      <th width="10%">商户订单号</th>
      <th width="10%">创建时间</th>
      <th width="8%">业务类型</th>
      <th width="6%">支付方式</th>
      <th width="6%">支付类型</th>
      <th width="12%">支付流水号</th>
      <th width="12%">银行订单号</th>
      <th width="6%">订单金额</th>
      <th width="10%">状态</th>
      <th width="10%">成功支付时间</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${pageBean.recordList}" varStatus="s">
      <tr>
        <td>${s.index + 1}</td>
        <td>${item.merchantNo}</td>
        <td>${item.merchantName}</td>
        <td>${item.merchantOrderNo}</td>
        <td><fmt:formatDate value="${item.createTime}"
                            pattern="yyyy-MM-dd HH:mm:ss" /></td>
        <td>
          <c:forEach items="${trxTypeEnums}" var="trxTypeVar">
            <c:if test="${item.trxType == trxTypeVar.key}">${trxTypeVar.value.desc}</c:if>
          </c:forEach>
        </td>
        <td>${item.payWayName}</td>
        <td>${item.payTypeName}</td>
        <td>${item.trxNo}</td>
        <td>${item.bankOrderNo}</td>
        <td>
          <fmt:parseNumber type="number" pattern="#,#00.0#">${item.orderAmount}</fmt:parseNumber>
        </td>
        <td>
          <c:forEach items="${statusEnums}" var="statusVar">
            <c:if test="${item.status == statusVar.key}">${statusVar.value.desc}</c:if>
          </c:forEach>
        </td>
        <td><fmt:formatDate value="${item.paySuccessTime}"
        pattern="yyyy-MM-dd HH:mm:ss" /></td>
        </td>
      </tr>
    </c:forEach>
    <c:if test="${pageBean.totalCount==0}"><tr><td>暂无数据</td></tr></c:if>
    </tbody>
  </table>
  <%@include file="../common/pageBar.jsp" %>
</div>
