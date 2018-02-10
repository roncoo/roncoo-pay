<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@include file="../../common/taglib/taglib.jsp"%>
<form id="pagerForm" method="post" action="${baseURL }/account/historyList">
    <%@include file="../common/pageParameter.jsp" %>
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${baseURL }/account/historyList"
		method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
				    <td>账户编号：
                        <input type="text" name="accountNo" value="${rpAccountHistory.accountNo}" />
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
				<th width="5%">变动金额</th>
				<th width="5%">变动方向</th>
				<th width="5%">余额</th>
				<th width="5%">请求号</th>
				<th width="5%">业务类型</th>
				<th width="10%">创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${pageBean.recordList}" varStatus="s">
				<tr>
					<td>${s.index + 1}</td>
					<td>${item.accountNo}</td>
					<td>${item.userNo}</td>
					<td>${item.userName}</td>
					<td><fmt:formatNumber value="${item.amount}" pattern="0.00"/></td>
					<td>${item.fundDirectionDesc}</td>
					<td><fmt:formatNumber value="${item.balance}" pattern="0.00"/></td>
					<td>${item.requestNo }</td>
					<td>${item.trxTypeDesc }</td>
					<td><fmt:formatDate value="${item.createTime}"
					     pattern="yyyy-MM-dd HH:mm:ss" />
			        </td>
				</tr>
			</c:forEach>
			<c:if test="${pageBean.totalCount==0}"><tr><td>暂无数据</td></tr></c:if>
		</tbody>
	</table>
	<%@include file="../common/pageBar.jsp" %>
</div>
