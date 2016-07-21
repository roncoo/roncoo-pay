<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglib/taglib.jsp"%>
<div class="pageContent">
	<form action="${baseURL }/sett/launchSett" cssClass="pageForm required-validate"
        onsubmit="return validateCallback(this, navTabAjaxDone);" method="post" >
        <input type="hidden" name="navTabId" value="jsjlgl">
        <input type="hidden" name="callbackType" value="closeCurrent">
        <input type="hidden" name="forwardUrl" value="">
        <div class="tabsContent pageFormContent"  layoutH="56">
			<div>
				<fieldset>
					<legend>结算详情</legend>
					<dl>
							<dt>创建日期：</dt>
							<dd>
								<fmt:formatDate value="${settRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
							</dd>
					</dl>
                    <dl>
							<dt>用户编号：</dt>
							<dd>
								${settRecord.userNo }
							</dd>
					</dl>
					<dl>
							<dt>用户名：</dt>
							<dd>
								${settRecord.userName }
							</dd>
					</dl>
					<dl>
							<dt>结算金额：</dt>
							<dd>
								<fmt:formatNumber value="${settRecord.settAmount}" pattern="0.00"/> 
							</dd>
					</dl>
					<dl>
							<dt>状态：</dt>
							<dd>
								${settRecord.settStatusDesc }
							</dd>
					</dl>
					<dl>
							<dt>备注：</dt>
							<dd>
								${settRecord.remark }
							</dd>
					</dl>
					<dl>
							<dt>银行名称：</dt>
							<dd>
								${settRecord.bankName }
							</dd>
					</dl>
					<dl>
							<dt>开户名：</dt>
							<dd>
								${settRecord.bankAccountName }
							</dd>
					</dl>
					<dl>
							<dt>开户账户：</dt>
							<dd>
								${settRecord.bankAccountNo }
							</dd>
					</dl>
					<dl>
							<dt>打款金额：</dt>
							<dd>
								${settRecord.remitAmount }
							</dd>
					</dl>
					<dl>
							<dt>打款备注：</dt>
							<dd>
								${settRecord.remitRemark }
							</dd>
					</dl>
					<dl>
							<dt>打款时间：</dt>
							<dd>
								<fmt:formatDate value="${settRecord.remitConfirmTime}" pattern="yyyy-MM-dd HH:mm:ss" />
							</dd>
					</dl>
				</fieldset>
			</div>
		</div>
		<div class="formBar">
			<ul style="float: left;">
				
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">关闭</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>