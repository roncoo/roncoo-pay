<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglib/taglib.jsp"%>
<div class="pageContent">
	<form action="${baseURL }/sett/remit" cssClass="pageForm required-validate"
        onsubmit="return validateCallback(this, navTabAjaxDone);" method="post" >
        <input type="hidden" name="navTabId" value="jsjlgl">
        <input type="hidden" name="callbackType" value="closeCurrent">
        <input type="hidden" name="forwardUrl" value="">
        <input type="hidden" name="settId" value="${settRecord.id}">
        <div class="tabsContent pageFormContent"  layoutH="56">
			<div>
				<fieldset>
					<legend>打款确认</legend>
					<dl>
							<dt>打款状态：</dt>
							<dd>
								<input type="radio" name="settStatus" value="REMIT_SUCCESS" checked="checked"/>成功
								<input type="radio" name="settStatus" value="REMIT_FAIL"/>失败
							</dd>
					</dl>
					<dl>
							<dt>打款备注：</dt>
							<dd>
								<input type="text" name="remark" maxlength="128" class="required"/>
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