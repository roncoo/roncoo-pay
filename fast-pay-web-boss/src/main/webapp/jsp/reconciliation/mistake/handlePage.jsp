<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../common/taglib/taglib.jsp"%>
<div class="pageContent">
	<form action="${baseURL }/reconciliation/mistake/handle" cssClass="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" method="post">
		<input type="hidden" name="navTabId" value="dzcclb" /> <input type="hidden" name="callbackType" value="closeCurrent" /> <input type="hidden" name="forwardUrl" value="" /> <input type="hidden" name="id" value="${mistake.id}" />
		<div class="tabsContent pageFormContent" layoutH="56">
			<div>
				<fieldset>
					<legend>差错信息</legend>

					<dl>
						<dt>商户订单号：</dt>
						<dd>${mistake.orderNo}</dd>
					</dl>

					<dl>
						<dt>银行订单号：</dt>
						<dd>${mistake.bankOrderNo}</dd>
					</dl>

					<dl>
						<dt>平台订单金额：</dt>
						<dd>
							<fmt:formatNumber value='${mistake.orderAmount}' pattern='##0.00' />
						</dd>
					</dl>
					<dl>
						<dt>平台订单手续费：</dt>
						<dd>
							<fmt:formatNumber value='${mistake.fee}' pattern='##0.00' />
						</dd>
					</dl>
					<dl>
						<dt>平台订单状态：</dt>
						<dd>
							<c:forEach items="${tradeStatusEnums}" var="tradeStatusEnum">
								<c:if test="${tradeStatusEnum.name eq mistake.tradeStatus }">
									${tradeStatusEnum.desc }
								</c:if>
							</c:forEach>

						</dd>
					</dl>

					<dl>
						<dt>银行订单金额：</dt>
						<dd>
							<fmt:formatNumber value='${mistake.bankAmount}' pattern='##0.00' />
						</dd>
					</dl>
					<dl>
						<dt>银行订单手续费：</dt>
						<dd>
							<fmt:formatNumber value='${mistake.bankFee}' pattern='##0.00' />
						</dd>
					</dl>
					<dl>
						<dt>银行订单状态：</dt>
						<dd>
							<c:if test="${mistake.bankTradeStatus ne null }">
								成功
							</c:if>
						</dd>
					</dl>
				</fieldset>
				<br /> <br />
				<fieldset>
					<legend>差错处理</legend>
					<dl>
						<dt>处理方式：</dt>
						<dd>
							<select name="handleType" id="handleType">
								<c:if test="${'PLATFORM_SHORT_STATUS_MISMATCH' ne mistake.errType }">
									<c:if test="${'PLATFORM_SHORT_CASH_MISMATCH' ne mistake.errType }">
										<c:if test="${'FEE_MISMATCH' ne mistake.errType }">
										</c:if>
									</c:if>
								</c:if>
								<option value="plat">以平台为准</option>
								<option value="bank">以银行为准</option>
							</select>
						</dd>
					</dl>
					<dl>
						<dt>差错类型：</dt>
						<dd>
							<c:forEach var="item" items="${reconciliationMistakeTypeEnums}">
								<c:if test="${item.name eq mistake.errType}">
									<input type="text" name="errType" value="${item.desc }" />
								</c:if>
							</c:forEach>

						</dd>
					</dl>
					<div class="divider"></div>
					<dl>
						<dt>处理备注：</dt>
						<dd>
							<textarea name="handleRemark" class="required" cols="80" rows="4"></textarea>
						</dd>
					</dl>
					<br /> <br /> <br /> <br /> <br /> <br /> <br /> <br />
				</fieldset>
				<br /> <br />


				<fieldset>
					<legend>差错处理解释</legend>
					<c:choose>
						<c:when test="${'BANK_MISS' eq mistake.errType}">
							<span style="color: red">差错类型为：银行漏单(平台支付成功，银行没有订单)。</span>
							<br />
							<br />
							<span style="color: red">以平台为准：只需要修改差错状态。</span>
							<br />
							<br />
							<span style="color: red">以银行为准：需要把该订单处理为支付失败，并把金额从商户账户减掉,修改差错状态。</span>
						</c:when>

						<c:when test="${'PLATFORM_SHORT_STATUS_MISMATCH' eq mistake.errType}">
							<span style="color: red">差错类型为：平台短款，状态不符(银行支付成功，平台支付不成功)。</span>
							<br />
							<br />
							<span style="color: red">以平台为准：只需要修改差错状态。</span>
							<br />
							<br />
							<span style="color: red">以银行为准：默认以银行为准，把支付记录状态改为成功，并给相应商家账户加钱，修改差错状态。</span>
						</c:when>

						<c:when test="${'PLATFORM_SHORT_CASH_MISMATCH' eq mistake.errType}">
							<span style="color: red">差错类型为：平台短款，金额不符(平台需支付金额比银行实际支付金额少)。</span>
							<br />
							<br />
							<span style="color: red">以平台为准：只需要修改差错状态。</span>
							<br />
							<br />
							<span style="color: red">以银行为准：默认以银行为准，增加支付记录金额，并给相应商家账户累加相应的差额，修改差错状态。</span>
						</c:when>

						<c:when test="${'PLATFORM_OVER_CASH_MISMATCH' eq mistake.errType}">
							<span style="color: red">差错类型为：平台长款,金额不符(银行实际支付金额比平台需支付金额少)。</span>
							<br />
							<br />
							<span style="color: red">以平台为准：修改差错状态。</span>
							<br />
							<br />
							<span style="color: red">以银行为准：减少支付记录，并给相应商家账户减少相应的差额，修改差错状态。</span>
						</c:when>

						<c:when test="${'PLATFORM_OVER_STATUS_MISMATCH' eq mistake.errType}">
							<span style="color: red">差错类型为：平台长款,状态不符(平台支付成功，银行支付不成功)。</span>
							<br />
							<br />
							<span style="color: red">以平台为准：修改差错状态。</span>
							<br />
							<br />
							<span style="color: red">以银行为准：需要把该订单处理为支付失败，并把金额从商户账户减掉,修改差错状态。</span>
						</c:when>

						<c:when test="${'FEE_MISMATCH' eq mistake.errType}">
							<span style="color: red">差手续费不匹配。</span>
							<br />
							<br />
							<span style="color: red">以平台为准：只需要修改差错状态。</span>
							<br />
							<br />
							<span style="color: red">以银行为准：修改订单手续费,修改差错状态。</span>
						</c:when>
					</c:choose>
				</fieldset>
			</div>
		</div>
		<div class="formBar">
			<ul style="float: left;">
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">提交</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
<script>
	
</script>