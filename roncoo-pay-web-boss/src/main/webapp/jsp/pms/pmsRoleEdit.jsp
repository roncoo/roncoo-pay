<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglib/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="${baseURL}/pms/role/edit" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
			<input type="hidden" name="navTabId" value="jsgl">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">

			<input type="hidden" name="id" value="${pmsRole.id }">

			<p>
				<label>角色名称：</label>
				<input type="text" name="roleName" cssClass="required" minlength="3" maxlength="90" size="30" value="${pmsRole.roleName }">
			</p>

			<p>
				<label>角色编码：</label>
				<input type="text" name="roleCode" cssClass="required" minlength="3" maxlength="90" size="30" value="${pmsRole.roleCode }">
			</p>

			<p style="height: 50px;">
				<label>角色描述：</label>
				<textarea rows="5" cols="27" name="remark" class="required" minlength="3" maxlength="300">${pmsRole.remark }</textarea>
			</p>

		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
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
