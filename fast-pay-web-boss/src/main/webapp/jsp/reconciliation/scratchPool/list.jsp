<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../common/taglib/taglib.jsp"%>
<form id="pagerForm" method="post" action="${baseURL }/reconciliation/list/scratchPool">
	<%@include file="../../common/pageParameter.jsp"%>
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${baseURL }/reconciliation/list/scratchPool" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
				</tr>
			</table>
		</div>
	</form>
</div>
<div class="pageContent">
	<table class="table" width="101%" layoutH="60">
		<thead>
			<tr>
				<th width="5%">序号</th>
				<th width="15%">商户订单号</th>
				<th width="15%">交易流水号</th>
				<th width="10%">银行订单号</th>
				<th width="10%">订单金额</th>
				<th width="10%">平台手续费</th>
				<th width="10%">支付方式</th>
				<th width="15%">支付成功时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${pageBean.recordList}" varStatus="s">
				<tr>
					<td>${s.index + 1}</td>
					<td>${item.merchantOrderNo}</td>
					<td>${item.trxNo}</td>
					<td>${item.bankOrderNo}</td>
					<td>${item.orderAmount}</td>
					<td>${item.platCost}</td>
					<td>${item.payWayCode}</td>
					<td><fmt:formatDate value="${item.paySuccessTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
			</c:forEach>
			<c:if test="${pageBean.totalCount==0}">
				<tr>
					<td>暂无数据</td>
				</tr>
			</c:if>
		</tbody>
	</table>
	<%@include file="../../common/pageBar.jsp"%>
</div>
