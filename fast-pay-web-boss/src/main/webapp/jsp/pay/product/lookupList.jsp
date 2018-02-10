<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@include file="../../../common/taglib/taglib.jsp"%>
<form id="pagerForm" method="post" action="${baseURL }/pay/product/lookupList">
    <%@include file="../../common/pageParameter.jsp" %>
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return dwzSearch(this, 'dialog');" action="${baseURL }/pay/product/lookupList"
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
		</ul>
	</div>
	<table class="table" width="101%" layoutH="114">
		<thead>
			<tr>
				<th width="5%">序号</th>
				<th width="15%">支付产品编号</th>
				<th width="10%">支付产品名称</th>
				<th width="15%">创建时间</th>
				<th width="15%">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${pageBean.recordList}" varStatus="s">
				<tr target="sid_user" rel="${id}" 
				ondblclick="$.bringBack({productCode:'${item.productCode}', productName:'${item.productName}'});">
					<td>${s.index + 1}</td>
					<td>${item.productCode}</td>
					<td>${item.productName}</td>
					<td><fmt:formatDate value="${item.createTime}"
					     pattern="yyyy-MM-dd HH:mm:ss" />
			        </td>
			        <td>
			        <a class="btnSelect" href="javascript:$.bringBack({productCode:'${item.productCode}', productName:'${item.productName}'})" title="查找带回">选择</a>
					</td>
				</tr>
			</c:forEach>
			<c:if test="${pageBean.totalCount==0}"><tr><td>暂无数据</td></tr></c:if>
		</tbody>
	</table>
	<%@include file="../../common/pageBar.jsp" %>
</div>