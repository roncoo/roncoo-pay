<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglib/taglib.jsp"%>
<form id="pagerForm" method="post" action="${baseURL }/pms/operator/list">
  <%@include file="../common/pageParameter.jsp" %>
</form>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="${baseURL }/pms/operator/list" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						操作员登录名：
						<input type="text" name="loginName" value="${param.loginName}" size="30" alt="精确查询" />
					</td>
					<td>
						操作员姓名：
						<input type="text" name="realName" value="${param.realName}" size="30" alt="模糊查询" />
					</td>
					<td>状态：</td>
					<td>
						<select name="status" class="combox">
							<option value="">-全部-</option>
							<option value="ACTIVE" <c:if test="${param.status eq'ACTIVE' }">selected = 'selected'</c:if>>-激活-</option>
							<option value="UNACTIVE" <c:if test="${param.status eq'UNACTIVE' }">selected = 'selected'</c:if>>-冻结-</option>
						</select>
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
				<a class="add" href="${baseURL}/pms/operator/addUI" target="dialog" rel="input" title="添加操作员">
					<span>添加操作员</span>
				</a>
			</li>
		</ul>
	</div>

	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="111">
		<thead>
			<tr>
				<th>序号</th>
				<th>创建时间</th>
				<th>操作员登录名</th>
				<th>操作员姓名</th>
				<th>手机号码</th>
				<th>状态</th>
				<th>操作</th>
				<!-- 图标列不能居中 -->
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${pageBean.recordList}" varStatus="st">
				<%-- 普通操作员看不到超级管理员信息 --%>
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
					<td>
						<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td>${item.loginName }</td>
					<td>${item.realName }</td>
					<td>${item.mobileNo }</td>
					<td>
						<c:forEach items="${OperatorStatusEnum}" var="operatorStatus">
							<c:if test="${item.status eq operatorStatus.value}">${operatorStatus.desc}</c:if>
						</c:forEach>
					</td>

					<td>
						[
						<a href="${baseURL}/pms/operator/viewUI?id=${item.id}" title="查看【${item.loginName }】详情" target="dialog" style="color: blue">查看</a>
						]
						<c:if test="${item.type eq OperatorTypeEnum.USER.value }">
									&nbsp;[<a href="${baseURL}/pms/operator/editUI?id=${item.id}" title="修改【${item.loginName }】" target="dialog" rel="operatorUpdate" style="color: blue">修改</a>]
									&nbsp;[<a href="${baseURL}/pms/operator/resetPwdUI?id=${item.id}" title="重置【${item.loginName }】的密码" target="dialog" width="550" height="300" style="color: blue">重置密码</a>]
									
									<c:if test="${item.type eq OperatorTypeEnum.USER.value && item.status==OperatorStatusEnum.ACTIVE.value}">
									&nbsp;[<a href="${baseURL}/pms/operator/changeStatus?id=${item.id}" title="冻结【${item.loginName }】" target="ajaxTodo" style="color: blue">冻结</a>]
									</c:if>

							<c:if test="${item.type eq OperatorTypeEnum.USER.value && item.status==OperatorStatusEnum.UNACTIVE.value}">
									&nbsp;[<a href="${baseURL}/pms/operator/changeStatus?id=${item.id}" title="激活【${item.loginName }】" target="ajaxTodo" style="color: blue">激活</a>]
									</c:if>

							<c:if test="${item.type eq OperatorTypeEnum.USER.value }">
									&nbsp;[<a href="${baseURL}/pms/operator/delete?id=${item.id}" target="ajaxTodo" title="确定要删除吗？" style="color: blue">删除</a>]
									</c:if>

						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页条 -->
	<%@include file="../common/pageBar.jsp"%>
</div>