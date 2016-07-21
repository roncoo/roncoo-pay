<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglib/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="pms_operatorChangeOwnPwd.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
			<input type="hidden" name="navTabId" value="">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
				<div class="unit">
					<label>旧密码：</label>
					<input type="password" name="oldPwd" class="required" size="30" />
				</div>
				<div class="unit">
					<label>新密码：</label>
					<input type="password" id="newPwd" name="newPwd" class="required" minlength="6" maxlength="20" size="30" />
				</div>
				<div class="unit">
					<label>确认新密码：</label>
					<input type="password" name="newPwd2" equalTo="#newPwd" class="required" minlength="6" maxlength="20" size="30" />
				</div>
				<div class="unit">
				</div>
				<div class="unit">
					<label></label>
					<span style="color:red;">提示：修改密码后要重新登录才能操作！</span>
				</div>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive"><div class="buttonContent"><button type="submit" >保存</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
