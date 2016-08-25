<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglib/taglib.jsp"%>
<form id="pagerForm" method="post" action="${baseURL }/pms/permission/list">
  <%@include file="../common/pageParameter.jsp" %>
</form>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="${baseURL}/pms/permission/list" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						权限名称：
						<input type="text" name="permissionName" value="${param.permissionName}" size="30" alt="模糊查询" />
					</td>
					<td>
						权限标识：
						<input type="text" name="permission" value="${param.permission}" size="30" alt="精确查询" />
					</td>
					<td>
						<div class="subBar">
							<ul>
								<li>
									<div class="buttonActive">
										<div class="buttonContent">
											<button type="submit">查询</button>
										</div>
									</div>
								</li>
							</ul>
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
				<a class="add" href="${baseURL}/pms/permission/addUI" target="dialog" width="550" height="350" rel="input" title="添加权限">
					<span>添加权限</span>
				</a>
			</li>
		</ul>
	</div>

	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="111">
		<thead>
			<tr>
				<th>序号</th>
				<th>权限名称</th>
				<th>权限标识</th>
				<th>描述</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${pageBean.recordList}" varStatus="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
					<td>${item.permissionName }</td>
					<td>${item.permission }</td>
					<td>${item.remark}</td>
					<td>
						<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td>
						[
						<a href="${baseURL}/pms/permission/editUI?id=${item.id}" title="修改权限" target="dialog" width="550" height="300" rel="input" style="color: blue">修改</a>
						] &nbsp;[
						<a href="${baseURL}/pms/permission/delete?permissionId=${item.id}" title="删除权限【${item.permission }】" target="ajaxTodo" style="color: blue">删除</a>
						]
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页条 -->
	<%@include file="../common/pageBar.jsp"%>
</div>
