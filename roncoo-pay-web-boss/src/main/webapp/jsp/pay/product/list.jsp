<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@include file="../../../common/taglib/taglib.jsp"%>
<form id="pagerForm" method="post" action="${baseURL }/pay/product/list">
    <%@include file="../../common/pageParameter.jsp" %>
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${baseURL }/pay/product/list"
		method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
				    <td>支付产品名称：
                        <input type="text" name="productName" value="${rpPayProduct.productName}" />
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
				<shiro:hasPermission name="pay:product:add">
				<a title="添加支付产品" class="add"
					href="${baseURL }/pay/product/addUI" target="navTab"><span>添加支付产品</span>
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
				<th width="15%">支付产品编号</th>
				<th width="10%">支付产品名称</th>
				<th width="10%">是否上架</th>
				<th width="15%">创建时间</th>
				<th width="15%">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${pageBean.recordList}" varStatus="s">
				<tr>
					<td>${s.index + 1}</td>
					<td>${item.productCode}</td>
					<td>${item.productName}</td>
					<td>${item.auditStatusDesc}</td>
					<td><fmt:formatDate value="${item.createTime}"
					     pattern="yyyy-MM-dd HH:mm:ss" />
			        </td>
			        <td>
						<c:if test="${item.auditStatus=='NO'}">
						<a title="设置支付方式" target="navTab" href="${baseURL }/pay/way/list?payProductCode=${item.productCode}" style="color: blue;" rel="zftdgl"> 设置支付方式</a>
						
						<shiro:hasPermission name="pay:product:add">
						<a title="确定要上架吗?" target="ajaxTodo" method="remove" 
                        href="${baseURL }/pay/product/audit?productCode=${item.productCode}&auditStatus=YES" style="color: blue;"> 上架</a>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="pay:product:delete">
                        <a title="确定要删除吗?" target="ajaxTodo" method="remove" 
                        href="${baseURL }/pay/product/delete?productCode=${item.productCode}" style="color: blue;"> 删除</a>
                        </shiro:hasPermission>
                        </c:if>
                        <c:if test="${item.auditStatus=='YES'}">
                        <a title="查看支付方式" target="navTab" href="${baseURL }/pay/way/list?payProductCode=${item.productCode}" style="color: blue;" rel="zftdgl"> 查看支付方式</a>
						<shiro:hasPermission name="pay:product:add">
						<a title="确定要下架吗?" target="ajaxTodo" method="remove" 
                        href="${baseURL }/pay/product/audit?productCode=${item.productCode}&auditStatus=NO" style="color: blue;"> 下架</a>
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
