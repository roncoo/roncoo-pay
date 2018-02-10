<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglib/taglib.jsp"%>
<div class="pageContent">
	<form>
		<div class="pageFormContent" layoutH="60">

			<p style="width: 99%">
				<label>操作员姓名：</label>
				<input type="text" name="realName" readonly="true" minlength="2" maxlength="45" size="30" value="${pmsOperator.realName }"/>
			</p>
			<p style="width: 99%">
				<label>操作员登录名：</label>
				<input type="text" name="loginName" readonly="true" minlength="2" maxlength="45" size="30" value="${pmsOperator.loginName }"/>
			</p>
			<p style="width: 99%">
				<label>创建时间：</label>
				<fmt:formatDate value="${pmsOperator.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
			</p>
			<p style="width: 99%">
				<label>手机号码：</label>
				<input type="text" name="mobileNo" readonly="true" minlength="2" maxlength="45" size="30" value="${pmsOperator.mobileNo }"/>
			</p>
			<p style="width: 99%">
				<label>状态：</label>
				<c:choose>
					<c:when test="${pmsOperator.status eq 'ACTIVE' }">激活</c:when>
					<c:when test="${pmsOperator.status eq 'UNACTIVE' }">冻结</c:when>
					<c:otherwise>--</c:otherwise>
				</c:choose>
			</p>
			<p style="width: 99%">
				<label>类型：</label>
				<c:choose>
					<c:when test="${pmsOperator.type eq 'ADMIN' }">普通操作员</c:when>
					<c:when test="${pmsOperator.type eq 'USER' }">超级管理员</c:when>
					<c:otherwise>--</c:otherwise>
				</c:choose>
			</p>
			<p style="width: 99%; height: 50px;">
				<label>描述：</label>
				<textarea name="remark" maxlength="100" rows="3" cols="30">${pmsOperator.remark }</textarea>
			</p>

			<fieldset style="width: 99%">
				<legend>关联的角色</legend>
				<c:forEach var="item" items="${rolesList}" varStatus="st">
					<c:choose>
						<c:when test="${pmsOperator.type eq 'ADMIN'}">
							<label> <input type="checkbox" <c:if test="${pmsOperator.type eq 'ADMIN'}">disabled="disabled"</c:if> cssClass="required" name="selectRole" id="${item.id }">${item.roleName }
							</label>
						</c:when>
						<c:when test="${pmsOperator.type eq 'USER'}">
							<label> <input type="checkbox" disabled="disabled" cssClass="required" name="selectRole" id="${item.id }">${item.roleName }
							</label>
						</c:when>
						<c:otherwise></c:otherwise>
					</c:choose>
				</c:forEach>
			</fieldset>

		</div>
		<div class="formBar">
			<ul>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div></li>
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
		for (var i = 0; i < array.length; i++) {
			$("#" + array[i]).attr("checked", "checked");
		}
	});
</script>