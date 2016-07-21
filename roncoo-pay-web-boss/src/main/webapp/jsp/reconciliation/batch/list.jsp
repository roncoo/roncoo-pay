<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../common/taglib/taglib.jsp"%>
<form id="pagerForm" method="post" action="${baseURL }/reconciliation/list/checkbatch">
	<%@include file="../../common/pageParameter.jsp"%>
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${baseURL }/reconciliation/list/checkbatch" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>账单时间: <input type="text" class="date" name="billDay" value="${billDay}" class="required" readonly="true" /> <a class="inputDateButton" href="javascript:;">选择</a>
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
				<th width="8%">差错总单数</th>
				<th width="4%">平台总交易单数</th>
				<th width="4%">银行总交易单数</th>
				<th width="8%">平台交易总金额</th>
				<th width="10%">银行交易总金额</th>
				<th width="4%">平台总手续费</th>
				<th width="4%">银行总手续费</th>
				<th width="4%">状态</th>
				<th width="10%">银行返回的错误信息</th>
				<th width="5%">解析失败异常</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${pageBean.recordList}" varStatus="s">
				<tr>
					<td>${s.index + 1}</td>
					<td>${item.batchNo}</td>
					<td>${item.bankType}</td>
					<td><fmt:formatDate value="${item.billDate}" pattern="yyyy-MM-dd" /></td>
					
					<td>${item.mistakeCount}</td>
					<td>${item.tradeCount}</td>
					<td>${item.bankTradeCount}</td>
					<td>${item.tradeAmount}</td>
					<td>${item.bankTradeAmount}</td>
					<td>${item.fee}</td>
					<td>${item.bankFee}</td>
					<td>${item.status}</td>
					<td>${item.bankErrMsg}</td>
					<td>${item.checkFailMsg}</td>
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
