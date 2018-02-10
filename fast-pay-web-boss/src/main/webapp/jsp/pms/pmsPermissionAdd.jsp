<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglib/taglib.jsp"%>
<div class="pageContent">
	<form id="form1" method="post" action="${baseURL}/pms/permission/add" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="58">
			<input type="hidden" name="navTabId" value="qxgl">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">

			<p>
				<label>权限名称：</label>
				<input type="text" name="permissionName" class="required" minlength="3" maxlength="50" size="30" />
			</p>
			<p>
				<label>权限标识：</label>
				<input type="text" name="permission" class="required" minlength="3" maxlength="50" size="30" />
				<span class="info"></span>
			</p>
			<p style="width: 99%">
				<label></label>
				<span style="color: red;">提示：权限标识添加后将不可修改，请确保添加信息的准确性！</span>
			</p>
			<p style="height: 50px;">
				<label>权限描述：</label>
				<textarea rows="3" cols="27" name="remark" class="required" minlength="3" maxlength="60"></textarea>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="submitForm()">保存</button>
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
<script type="text/javascript">
	function submitForm() {
		$("#form1").submit();
	}

	// 查找带回的拓展功能
	$.extend({
		bringBackSuggest : function(args) {
			$("input[name='menu.id']").val(args["id"]);
			$("input[name='menu.name']").val(args["name"]);
		},
		bringBack : function(args) {
			$.bringBackSuggest(args);
			$.pdialog.closeCurrent();
		}
	});
</script>
