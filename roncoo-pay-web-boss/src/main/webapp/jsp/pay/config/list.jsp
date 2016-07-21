<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@include file="../../../common/taglib/taglib.jsp"%>
<form id="pagerForm" method="post" action="${baseURL }/pay/config/list">
    <%@include file="../../common/pageParameter.jsp" %>
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${baseURL }/pay/config/list"
		method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
				    <td>用户名：
                        <input type="text" name="userName" value="${rpUserPayConfig.userName}" />
                    </td>
                    <td>支付产品名称：
                        <input type="text" name="productName" value="${rpUserPayConfig.productName}" />
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
				<shiro:hasPermission name="pay:config:add">
				<a title="添加支付配置" class="add"
					href="${baseURL }/pay/config/addUI" target="navTab"><span>添加支付配置</span>
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
				<th width="12%">支付产品名称</th>
				<th width="12%">支付key</th>
				<th width="8%">收款方式</th>
				<th width="12%">支付密钥</th>
				<th width="8%">创建时间</th>
				<th width="15%">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${pageBean.recordList}" varStatus="s">
				<tr>
					<td>${s.index + 1}</td>
					<td>${item.userName}</td>
					<td>${item.productName}</td>
					<td>${item.payKey}</td>
					<td>${item.fundIntoTypeDesc}</td>
					<td>${item.paySecret}</td>
					<td><fmt:formatDate value="${item.createTime}"
					     pattern="yyyy-MM-dd HH:mm:ss" />
			        </td>
			        <td>
			        	<shiro:hasPermission name="pay:config:edit">
			        	<a title="绑定出款银行卡" target="navTab" href="${baseURL }/pay/config/editBankUI?userNo=${item.userNo}" style="color: blue;" rel="zftdgl"> 绑定出款银行卡</a>
			        	<a title="修改" target="navTab" href="${baseURL }/pay/config/editUI?userNo=${item.userNo}" style="color: blue;" rel="zftdgl"> 修改</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="pay:config:delete">
						<a title="确定要删除吗?" target="ajaxTodo" method="remove" 
                        href="${baseURL }/pay/config/delete?userNo=${item.userNo}" style="color: blue;"> 删除</a>
                        </shiro:hasPermission>
			        </td>
				</tr>
			</c:forEach>
			<c:if test="${pageBean.totalCount==0}"><tr><td>暂无数据</td></tr></c:if>
		</tbody>
	</table>
	<%@include file="../../common/pageBar.jsp" %>
</div>
