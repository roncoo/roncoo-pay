<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@include file="../../../common/taglib/taglib.jsp"%>
<form id="pagerForm" method="post" action="${baseURL }/pay/way/list">
    <%@include file="../../common/pageParameter.jsp" %>
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${baseURL }/pay/way/list"
		method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
				    <td>支付方式名称：
                        <input type="text" name="payWayName" value="${rpPayWay.payWayName}" />
                        <input type="hidden" name="payProductCode" value="${rpPayWay.payProductCode}" />
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
				<shiro:hasPermission name="pay:way:add">
				<c:if test="${rpPayProduct.auditStatus=='NO'}">
				<a title="设置支付方式" class="add"
					href="${baseURL }/pay/way/addUI?payProductCode=${rpPayWay.payProductCode}" target="navTab"><span>设置支付方式</span>
				</a>
				</c:if>
				</shiro:hasPermission>
				</li>
				<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="101%" layoutH="109">
		<thead>
			<tr>
				<th width="5%">序号</th>
				<th width="15%">支付方式编号</th>
				<th width="10%">支付方式名称</th>
				<th width="15%">支付类型编号</th>
				<th width="10%">支付类型名称</th>
				<th width="10%">费率(%)</th>
				<th width="15%">创建时间</th>
				<th width="10%">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${pageBean.recordList}" varStatus="s">
				<tr>
					<td>${s.index + 1}</td>
					<td>${item.payWayCode}</td>
					<td>${item.payWayName}</td>
					<td>${item.payTypeCode}</td>
					<td>${item.payTypeName}</td>
					<td>${item.payRate}</td>
					<td><fmt:formatDate value="${item.createTime}"
					     pattern="yyyy-MM-dd HH:mm:ss" />
			        </td>
			        <td>
			        	<c:if test="${rpPayProduct.auditStatus=='NO'}">
			        	<shiro:hasPermission name="pay:way:edit">
			        	<a title="编辑" target="navTab" href="${baseURL }/pay/way/editUI?id=${item.id}" style="color: blue;"> 编辑</a>
			        	</shiro:hasPermission>
			        	<shiro:hasPermission name="pay:way:delete">
						<a title="确定要删除吗?" target="ajaxTodo" method="remove" 
                        href="${baseURL }/pay/way/delete?id=${item.id}" style="color: blue;"> 删除</a>
                        </shiro:hasPermission>
                        </c:if>
			        </td>
				</tr>
			</c:forEach>
			<c:if test="${pageBean.totalCount==0}"><tr><td>暂无数据</td></tr></c:if>
		</tbody>
	</table>
	<%@include file="../../common/pageBar.jsp" %>
</div>
