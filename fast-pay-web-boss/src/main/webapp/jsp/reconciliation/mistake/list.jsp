<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../common/taglib/taglib.jsp"%>
<form id="pagerForm" method="post" action="${baseURL }/reconciliation/list/mistake">
	<%@include file="../../common/pageParameter.jsp"%>
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${baseURL }/reconciliation/list/mistake" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						账单开始时间:
						<input type="text" class="date" name="billBeginDate" value="${billBeginDate}" class="required" readonly="true" />
						<a class="inputDateButton" href="javascript:;">选择</a>
					</td>
					<td>
						账单结束时间:
						<input type="text" class="date" name="billEndDate" value="${billEndDate}" class="required" readonly="true" />
						<a class="inputDateButton" href="javascript:;">选择</a>
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
	<table class="table" width="101%" layoutH="110">
		<thead>
			<tr>
				<th width="2%">序号</th>
				<th width="7%">对账批次号</th>
				<th width="4%">支付方式</th>
				<th width="5%">账单日期</th>
				<th width="8%">商家订单号</th>
				<th width="4%">平台交易金额</th>
				<th width="4%">平台手续费</th>
				<th width="8%">平台状态</th>
				<th width="10%">银行订单号</th>
				<th width="4%">银行交易金额</th>
				<th width="4%">银行手续费</th>
				<th width="4%">银行状态</th>
				<th width="10%">差错类型</th>
				<th width="5%">处理状态</th>
				<th width="5%">处理结果</th>
				<th width="10%">处理备注</th>
				<th width="15%">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${pageBean.recordList}" varStatus="s">
				<tr>
					<td>${s.index + 1}</td>
					<td>${item.accountCheckBatchNo}</td>
					<td>
						<c:forEach items="${payWayEnums}" var="payWayEnum">
							<c:if test="${payWayEnum.name eq item.bankType }"> ${payWayEnum.desc }</c:if>
						</c:forEach>
					</td>
					<td>
						<fmt:formatDate value="${item.billDate}" pattern="yyyy-MM-dd" />
					</td>
					<td>${item.orderNo}</td>
					<td>
						<fmt:formatNumber value='${item.orderAmount}' pattern='##0.00' />
					</td>
					<td>
						<fmt:formatNumber value='${item.fee}' pattern='##0.00' />
					</td>
					<td>
						<c:forEach items="${tradeStatusEnums}" var="tradeStatusEnum">
							<c:if test="${tradeStatusEnum.name eq item.tradeStatus }"> ${tradeStatusEnum.desc }</c:if>
						</c:forEach>
					</td>
					<td>${item.bankOrderNo}</td>
					<td>
						<fmt:formatNumber value='${item.bankAmount}' pattern='##0.00' />
					</td>
					<td>
						<fmt:formatNumber value='${item.bankFee}' pattern='##0.00' />
					</td>
					<td>
						<c:if test="${item.bankTradeStatus eq 'SUCCESS'}">成功</c:if>
					</td>
					<td>
						<c:forEach items="${reconciliationMistakeTypeEnums}" var="reconciliationMistakeTypeEnum">
							<c:if test="${reconciliationMistakeTypeEnum.name eq item.errType }">
								<font color="red">${reconciliationMistakeTypeEnum.desc }</font>
							</c:if>
						</c:forEach>
					</td>
					<td>
						<c:forEach items="${mistakeHandleStatusEnums}" var="mistakeHandleStatusEnum">
							<c:if test="${mistakeHandleStatusEnum.name eq item.handleStatus }"> ${mistakeHandleStatusEnum.desc }</c:if>
						</c:forEach>
					</td>
					<td>${item.handleValue}</td>
					<td>${item.handleRemark}</td>
					<td>
						<shiro:hasPermission name="recon:mistake:edit">
							<c:if test="${'NOHANDLE' eq item.handleStatus }">
								<a title="差错处理" target="navTab" href="${baseURL }/reconciliation/mistake/tohandlePage?id=${item.id}">
									<span style="color: blue;">差错处理</span>
								</a>

							</c:if>
						</shiro:hasPermission>
					</td>
				</tr>
			</c:forEach>
			<c:if test="${pageBean.totalCount==0}">
				<tr>
					<td>暂无数据</td>
				</tr>
			</c:if>
		</tbody>
	</table>
	<%@include file="../../common/pageBar.jsp"%>
</div>
