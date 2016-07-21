<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@include file="../../common/taglib/taglib.jsp"%>
<form id="pagerForm" method="post" action="${baseURL }/sett/list">
    <%@include file="../common/pageParameter.jsp" %>
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${baseURL }/sett/list"
		method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
				    <td>用户名：
                        <input type="text" name="userName" value="${rpSettRecord.userName}" />
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
				<shiro:hasPermission name="sett:record:add">
				<a title="发起结算" class="add"
					href="${baseURL }/sett/launchSettUI" target="navTab"><span>发起结算</span>
				</a>
				</shiro:hasPermission>
				</li>
				<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="101%" layoutH="109">
		<thead>
			<tr>
				<th width="5%">序号</th>
				<th width="10%">用户名</th>
				<th width="12%">结算金额</th>
				<th width="12%">银行名称</th>
				<th width="12%">开户名</th>
				<th width="12%">开户账户</th>
				<th width="12%">收款手机号</th>
				<th width="12%">结算状态</th>
				<th width="8%">创建时间</th>
				<th width="15%">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${pageBean.recordList}" varStatus="s">
				<tr>
					<td>${s.index + 1}</td>
					<td>${item.userName}</td>
					<td><fmt:formatNumber value="${item.settAmount}" pattern="0.00"/> 
			        </td>
					<td>${item.bankName}</td>
					<td>${item.bankAccountName}</td>
					<td>${item.bankAccountNo}</td>
					<td>${item.mobileNo}</td>
					<td>${item.settStatusDesc}</td>
					<td><fmt:formatDate value="${item.createTime}"
					     pattern="yyyy-MM-dd HH:mm:ss" />
			        </td>
			        <td>
			        	<a title="查看" target="navTab" href="${baseURL }/sett/view?id=${item.id}" style="color: blue;" rel="zftdgl"> 查看</a>
						<c:if test="${item.settStatus=='WAIT_CONFIRM'}">
						<shiro:hasPermission name="sett:record:edit">
						<a title="审核" target="navTab" href="${baseURL }/sett/auditUI?id=${item.id}" style="color: blue;" rel="sh"> 审核</a>
						</shiro:hasPermission>
						</c:if>
						<c:if test="${item.settStatus=='CONFIRMED'}">
						<shiro:hasPermission name="sett:record:edit">
						<a title="打款" target="navTab" href="${baseURL }/sett/remitUI?id=${item.id}" style="color: blue;" rel="dk"> 打款</a>
			        	</shiro:hasPermission>
			        	</c:if>
			        </td>
				</tr>
			</c:forEach>
			<c:if test="${pageBean.totalCount==0}"><tr><td>暂无数据</td></tr></c:if>
		</tbody>
	</table>
	<%@include file="../common/pageBar.jsp" %>
</div>
