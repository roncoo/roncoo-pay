<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglib/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="${baseURL }/pms/menu/add" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
			<input type="hidden" name="navTabId" value="listPmsMenu">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">

			<p style="width: 99%">
				<label>上级菜单：</label>
				<input type="text" name="parent.name" value="${pmsMenu.parent.name}" readonly="true" />
				<input type="hidden" name="parent.id" value="${pmsMenu.parent.id}" />
				<input type="hidden" name="parent.level" value="${pmsMenu.parent.level}" />
				<span class="info"></span>
			</p>
			<p style="width: 99%">
				<label>菜单名称：</label>
				<input type="text" name="name" class="required" maxlength="90" value="${pmsMenu.name}" />
			</p>
			<p style="width: 99%">
				<label>菜单编号：</label>
				<input type="text" name="number" class="required number" maxlength="20" value="${pmsMenu.number}" />
			</p>
			<p style="width: 99%">
				<label>请求URL：</label>
				<input type="text" name="url" maxlength="150" value="${pmsMenu.url}" size="50" />
			</p>
			<p style="width: 99%">
				<label>navTabId：</label>
				<input type="text" name="targetName" maxlength="50" value="${pmsMenu.targetName}" />
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