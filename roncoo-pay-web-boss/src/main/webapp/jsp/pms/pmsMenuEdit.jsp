<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglib/taglib.jsp"%>
<div class="pageContent">
	<form id="editPmsMenu1" method="post" action="${baseURL }/pms/menu/edit" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="150">
			<input type="hidden" name="navTabId" value="listPmsMenu">
			<input type="hidden" name="forwardUrl" value="">

			<input type="hidden" id="menuId" name="id" value="${pmsMenu.id }" />
			<input type="hidden" name="version" value="${pmsMenu.version }" />
			<input type="hidden" name="level" value="${pmsMenu.level }" />
			<input type="hidden" name="isLeaf" value="${pmsMenu.isLeaf }" />

			<p style="width: 99%">
				<label>上级菜单：</label>
				<input type="text" name="parent.name" value="${pmsMenu.parent.name}" readonly="true" size="30" />
				<input type="hidden" name="parent.id" value="${pmsMenu.parent.id}" />
				<span class="info"></span>
			</p>
			<p style="width: 99%">
				<label>菜单名称：</label>
				<input type="text" name="name" class="required" maxlength="90" value="${pmsMenu.name }" size="30" />
			</p>
			<p style="width: 99%">
				<label>菜单编号：</label>
				<input type="text" name="number" class="required number" maxlength="20" value="${pmsMenu.number }" size="30" />
			</p>
			<p style="width: 99%">
				<label>请求URL：</label>
				<input type="text" name="url" maxlength="150" value="${pmsMenu.url }" size="50" />
			</p>

			<p style="width: 99%">
				<label>navTabId：</label>
				<input type="text" name="targetName" maxlength="50" value="${pmsMenu.targetName}" size="30" />
			</p>
			<z:permission value="pms:operator:edit">
				<div class="buttonActive" style="margin-left: 130px; margin-top: 30px;">
					<div class="buttonContent">
						<button type="submit">保存</button>
					</div>
				</div>
			</z:permission>
		</div>
	</form>
</div>
<script type="text/javascript">
	function submitForm2() {
		$("#editPmsMenu1").submit();
	}
</script>