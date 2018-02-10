<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglib/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="${baseURL}/pms/operator/edit" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
		    <input type="hidden" name="navTabId" value="czygl">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			
			<input type="hidden" name="id" value="${pmsOperator.id }">
			<input type="hidden" name="selectVal" id="selectVal" value="">
			
			<p style="width:99%">
				<label>操作员姓名：</label>
				<input type="text" name="realName" class="required" minlength="2" maxlength="45" size="30" value="${pmsOperator.realName }"/>
			</p>
			<p style="width:99%">
				<label>操作员登录名：</label>
				<input type="text" name="loginName" class="required" readonly="true" minlength="2" maxlength="45" size="30" value="${pmsOperator.loginName }"/>
			</p>
			<p style="width:99%">
				<label>手机号码：</label>
				<input type="text" name="mobileNo" class="required mobile" maxlength="12" size="30" value="${pmsOperator.mobileNo }"/>
			</p>
			<p style="width:99%">
				<label>状态：${pmsOperator.status}</label>
				<c:choose>
					<c:when test="${pmsOperator.status eq OperatorStatusEnum.ACTIVE.value}">激活</c:when>
					<c:when test="${pmsOperator.status eq OperatorStatusEnum.INACTIVE.value}">冻结</c:when>
					<c:otherwise>--</c:otherwise>
				</c:choose>
			</p>
			<p style="width:99%">
				<label>操作员类型：</label>
				<c:choose>
					<c:when test="${pmsOperator.type eq OperatorTypeEnum.USER.value }">普通操作员</c:when>
					<c:when test="${pmsOperator.type eq OperatorTypeEnum.ADMIN.value }">超级管理员</c:when>
					<c:otherwise>--</c:otherwise>
				</c:choose>
			</p>
			<p style="width:99%;height:50px;">
				<label>描述：</label>
				<textarea name="remark" class="required" maxlength="100" rows="3" cols="30">${pmsOperator.remark }</textarea>
			</p>
			
			<fieldset style="width:99%">
				<legend>选择角色<font color="red">*</font></legend>
				<c:forEach items="${rolesList}" var="v">
					<label>
						<input type="checkbox" class="selectOperatorRole" name="selectRole" id="roleId${v.id }" value="${v.id }">${v.roleName }
					</label>
				</c:forEach>
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