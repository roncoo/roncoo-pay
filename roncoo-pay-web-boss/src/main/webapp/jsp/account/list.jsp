<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglib/taglib.jsp"%>
<form id="pagerForm" method="post" action="${baseURL }/account/list">
	<%@include file="../common/pageParameter.jsp"%>
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${baseURL }/account/list" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>账户编号： <input type="text" name="accountNo" value="${rpAccount.accountNo}" />
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
	<table class="table" width="101%" layoutH="79">
		<thead>
			<tr>
				<th width="5%">序号</th>
				<th width="15%">账户编号</th>
				<th width="15%">用户编号</th>
				<th width="10%">用户名</th>
				<th width="5%">账户余额</th>
				<th width="5%">可用余额</th>
				<th width="5%">可结算金额</th>
				<th width="5%">今日收益</th>
				<th width="5%">今日支出</th>
				<th width="10%">更新时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${pageBean.recordList}" varStatus="s">
				<tr>
					<td>${s.index + 1}</td>
					<td>${item.accountNo}</td>
					<td>${item.userNo}</td>
					<td>${item.userName}</td>
					<td><fmt:formatNumber value="${item.balance}" pattern="0.00" /></td>
					<td><fmt:formatNumber value="${item.availableBalance}" pattern="0.00" /></td>
					<td><fmt:formatNumber value="${item.availableSettAmount}" pattern="0.00" /></td>
					<td><fmt:formatNumber value="${item.todayIncome}" pattern="0.00" /></td>
					<td><fmt:formatNumber value="${item.todayExpend}" pattern="0.00" /></td>
					<td><fmt:formatDate value="${item.editTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
			</c:forEach>
			<c:if test="${pageBean.totalCount==0}">
				<tr>
					<td>暂无数据</td>
				</tr>
			</c:if>
		</tbody>
	</table>
	<%@include file="../common/pageBar.jsp"%>
</div>
