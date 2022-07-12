<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../common/taglib/taglib.jsp"%>
<div class="pageContent">
	<form action="${baseURL }/user/info/add" cssClass="pageForm required-validate"
        onsubmit="return validateCallback(this, navTabAjaxDone);" method="post" >
        <input type="hidden" name="navTabId" value="yhxx">
        <input type="hidden" name="callbackType" value="closeCurrent">
        <input type="hidden" name="forwardUrl" value="">
        <div class="tabsContent pageFormContent"  layoutH="56">
			<div>
				<fieldset>
					<legend>用户信息</legend>
                    <dl>
							<dt>用户名称：</dt>
							<dd>
								<input type="text" name="userName" size="25" maxlength="128" />
							</dd>
					</dl>
					<dl>
							<dt>手机号：</dt>
							<dd>
								<input type="text" name="mobile" size="25" maxlength="128" />
							</dd>
					</dl>
					<dl>
							<dt>登录密码：</dt>
							<dd>
								<input type="password" name="password" size="25" maxlength="128" />
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