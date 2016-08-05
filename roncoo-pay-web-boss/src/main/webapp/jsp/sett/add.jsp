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
					<legend>发起结算</legend>
                    <dl>
							<dt>用户编号：</dt>
							<dd>
								<input type="text" name="user.userNo" id="userNo" size="25" maxlength="128" class="required readonly" />
								<a class="btnLook" href="${baseURL}/user/info/lookupList" lookupGroup="user">搜索</a>
							</dd>
					</dl>
					<dl>
							<dt>用户名：</dt>
							<dd>
								<input type="text" name="user.userName" maxlength="80" class="required readonly" />
							</dd>
					</dl>
					<dl>
							<dt>可结算余额：</dt>
							<dd>
								<input type="text" name="settAmount" id="settAmount" size="10" maxlength="30" class="readonly" />
								&nbsp;<a href="javascript:void(0)" onclick="getSettAmount()" style="color: blue;">获取可结算余额</a>
							</dd>
					</dl>
					<dl>
							<dt>结算金额：</dt>
							<dd>
								<input type="text" name="settAmount" maxlength="128" class="required digits"/>
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
<script>
	function getSettAmount(){
		var userNo = $("#userNo").val();
		if(userNo != ''){
			$.ajax({  
	            type: "GET",
	            dataType : "json",
	            data:{userNo : userNo},
	            url: "${baseURL }/sett/getSettAmount",
	            //请求成功完成后要执行的方法  
	            success: function(obj){
	            	$("#settAmount").val(obj.availableSettAmount);
	            },  
	            error : function() {
	            	alert("系统异常！");  
	            }   
	        });
		}
	}
</script>