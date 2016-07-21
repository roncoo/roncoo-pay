<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../common/taglib/taglib.jsp"%>
<div class="pageContent">
	<form action="${baseURL }/pay/config/editBank" cssClass="pageForm required-validate"
        onsubmit="return validateCallback(this, navTabAjaxDone);" method="post" >
        <input type="hidden" name="navTabId" value="yhzfpz">
        <input type="hidden" name="callbackType" value="closeCurrent">
        <input type="hidden" name="forwardUrl" value="">
        <div class="tabsContent pageFormContent"  layoutH="56">
			<div>
				<fieldset>
					<legend>绑定出款银行卡</legend>
                    <dl>
							<dt>用户编号：</dt>
							<dd>
								<input type="text" name="userNo" size="25" maxlength="128" class="readonly" value="${rpUserInfo.userNo }"/>
							</dd>
					</dl>
					<dl>
							<dt>用户名：</dt>
							<dd>
								<input type="text" name="userName" maxlength="80" class="readonly" value="${rpUserInfo.userName }"/>
							</dd>
					</dl>
					<dl>
							<dt>银行名称：</dt>
							<dd>
								<select name="bankCode" id="bankCode">
		                            <c:forEach var="item" items="${BankCodeEnums}">
		                                <option value="${item.name }" <c:if test="${item.name==rpUserBankAccount.bankCode}">selected="selected"</c:if>>${item.desc }</option>
		                            </c:forEach>
	                        	</select>
							</dd>
					</dl>
					<dl>
							<dt>银行账户类型：</dt>
							<dd>
								<select name="bankAccountType" id="bankAccountType">
		                            <c:forEach var="item" items="${BankAccountTypeEnums}">
		                                <option value="${item.name }" <c:if test="${item.name==rpUserBankAccount.bankAccountType}">selected="selected"</c:if>>${item.desc }</option>
		                            </c:forEach>
	                        	</select>
							</dd>
					</dl>
					<dl>
							<dt>银行开户名：</dt>
							<dd>
								<input type="text" name="bankAccountName" maxlength="80" class="required" value="${rpUserBankAccount.bankAccountName }"/>
							</dd>
					</dl>
					<dl>
							<dt>银行账号：</dt>
							<dd>
								<input type="text" name="bankAccountNo" maxlength="30" class="required" value="${rpUserBankAccount.bankAccountNo }"/>
							</dd>
					</dl>
					<dl>
							<dt>证件类型：</dt>
							<dd>
								<select name="cardType" id="cardType">
		                            <c:forEach var="item" items="${CardTypeEnums}">
		                                <option value="${item.name }" <c:if test="${item.name==rpUserBankAccount.bankAccountType}">selected="selected"</c:if>>${item.desc }</option>
		                            </c:forEach>
	                        	</select>
							</dd>
					</dl>
					<dl>
							<dt>证件号码：</dt>
							<dd>
								<input type="text" name="cardNo" maxlength="30" class="required" value="${rpUserBankAccount.cardNo }"/>
							</dd>
					</dl>
					<dl>
							<dt>手机号：</dt>
							<dd>
								<input type="text" name="mobileNo" maxlength="80" class="required" value="${rpUserBankAccount.mobileNo }"/>
							</dd>
					</dl>
					<dl>
							<dt>开户行详细地址：</dt>
							<dd>
								<input type="text" name="street" maxlength="150" class="required" value="${rpUserBankAccount.street }"/>
							</dd>
					</dl>
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