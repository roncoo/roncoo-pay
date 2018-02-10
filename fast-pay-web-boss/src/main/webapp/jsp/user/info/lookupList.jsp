<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@include file="../../../common/taglib/taglib.jsp"%>
<form id="pagerForm" method="post" action="${baseURL }/user/info/lookupList">
    <%@include file="../../common/pageParameter.jsp" %>
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return dwzSearch(this, 'dialog');" action="${baseURL }/user/info/lookupList"
		method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
				    <td>用户名称：
                        <input type="text" name="userName" value="${rpUserInfo.userName}" />
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
		</ul>
	</div>
	<table class="table" width="101%" layoutH="114">
		<thead>
			<tr>
				<th width="5%">序号</th>
				<th width="15%">用户编号</th>
				<th width="10%">用户名称</th>
				<th width="10%">账户编号</th>
				<th width="10%">状态</th>
				<th width="15%">创建时间</th>
				<th width="15%">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${pageBean.recordList}" varStatus="s">
				<tr target="sid_user" rel="${id}" 
				ondblclick="$.bringBack({userNo:'${item.userNo}', userName:'${item.userName}'});">
					<td>${s.index + 1}</td>
					<td>${item.userNo}</td>
					<td>${item.userName}</td>
					<td>${item.accountNo}</td>
					<td>${item.statusDesc}</td>
					<td><fmt:formatDate value="${item.createTime}"
					     pattern="yyyy-MM-dd HH:mm:ss" />
			        </td>
			        <td>
			        <a class="btnSelect" href="javascript:$.bringBack({userNo:'${item.userNo}', userName:'${item.userName}'})" title="查找带回">选择</a>
					</td>
				</tr>
			</c:forEach>
			<c:if test="${pageBean.totalCount==0}"><tr><td>暂无数据</td></tr></c:if>
		</tbody>
	</table>
	<%@include file="../../common/pageBar.jsp" %>
</div>