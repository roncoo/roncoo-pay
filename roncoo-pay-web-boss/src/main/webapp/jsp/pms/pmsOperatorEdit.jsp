<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglib/taglib.jsp"%>
<style>
<!--
.pageFormContent fieldset label{
	width: 200px;
}
-->
</style>
<div class="pageContent">
	<form id="form" method="post" action="pms_editPmsOperator.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
		    <input type="hidden" name="navTabId" value="listPmsOperator">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			
			<input type="operatorId" name="id" value="">
			<input type="selectVal" name="selectVal" value="">
			
			<p style="width:99%">
				<label>操作员姓名：</label>
				<s:textfield name="realName" cssClass="required" minlength="2" maxlength="15" size="30" />
			</p>
			<p style="width:99%">
				<label>操作员登录名：</label>
				<s:textfield name="loginName" cssClass="required" readonly="true" minlength="3" maxlength="30" size="30" />
			</p>
			<p style="width:99%">
				<label>手机号码：</label>
				<s:textfield name="mobileNo" cssClass="required mobile"  maxlength="12" size="30" />
			</p>
			<p style="width:99%">
				<label>状态：</label>
				<c:choose>
					<c:when test="${status eq OperatorStatusEnum.ACTIVE.value}">激活</c:when>
					<c:when test="${status eq OperatorStatusEnum.INACTIVE.value}">冻结</c:when>
					<c:otherwise>--</c:otherwise>
				</c:choose>
			</p>
			<p style="width:99%">
				<label>操作员类型：</label>
				<c:choose>
					<c:when test="${type eq OperatorTypeEnum.USER.value }">普通操作员</c:when>
					<c:when test="${type eq OperatorTypeEnum.ADMIN.value }">超级管理员</c:when>
					<c:otherwise>--</c:otherwise>
				</c:choose>
			</p>
			<p style="width:99%;height:50px;">
				<label>描述：</label>
				<s:textarea name="remark" maxlength="100" rows="3" cols="30"></s:textarea>
			</p>
			
			<fieldset style="width:99%">
				<legend>选择角色<font color="red">*</font></legend>
				<s:iterator value="rolesList" status="st" var="v">
					<c:choose>
						<c:when test="${v.roleType eq RoleTypeEnum.ADMIN.value && type eq RoleTypeEnum.ADMIN.value}">
							<label>
								<input type="checkbox" <c:if test="${type eq RoleTypeEnum.ADMIN.value}">disabled="disabled"</c:if> 
								class="selectOperatorRole" name="selectRole" id="roleId${v.id }" value="${v.id }">${v.roleName }
							</label>
						</c:when>
						<c:when test="${v.roleType eq RoleTypeEnum.USER.value}">
							<label>
								<input type="checkbox" <c:if test="${type eq RoleTypeEnum.ADMIN.value}">disabled="disabled"</c:if> 
								class="selectOperatorRole" name="selectRole" id="roleId${v.id }" value="${v.id }">${v.roleName }
							</label>
						</c:when>
						<c:otherwise></c:otherwise>
					</c:choose>
				</s:iterator>
			</fieldset>
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="submitForm()">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
	//回显
	$(document).ready(function() {
		var str = "${owenedRoleIds}";
		var array = new Array();
		array = str.split(",");
		for ( var i = 0; i < array.length; i++) {
			$("#roleId" + array[i]).attr("checked", "checked");
		}
	});

	function submitForm() {
		var str = "";
		$(":checkbox:checked").each(function() {
			if ($(this).hasClass('selectOperatorRole')){
				// 加样式判断，避免与其他复选框冲突
				str += $(this).val() + ",";
			}
		});
		if(str == null || str == ""){
			alertMsg.error("操作员关联的角色不能为空");
			return;
		}
		$("#selectVal").val(str);
		$("#form").submit();
	}
	
</script>