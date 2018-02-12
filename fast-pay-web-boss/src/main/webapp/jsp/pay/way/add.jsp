<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../common/taglib/taglib.jsp"%>
<div class="pageContent">
	<form action="${baseURL }/pay/way/add" cssClass="pageForm required-validate"
        onsubmit="return validateCallback(this, navTabAjaxDone);" method="post" >
        <input type="hidden" name="navTabId" value="zftdgl">
        <input type="hidden" name="callbackType" value="closeCurrent">
        <input type="hidden" name="forwardUrl" value="">
        <input type="hidden" name="payProductCode" value="${payProductCode }"/>
        <div class="tabsContent pageFormContent"  layoutH="56">
			<div>
				<fieldset>
					<legend>设置支付方式</legend>
					
                    <dl>
							<dt>支付方式：</dt>
							<dd>
								<select name="payWayCode" id="payWayCode">
		                            <c:forEach var="item" items="${PayWayEnums}">
		                                <option value="${item.name }">${item.desc }</option>
		                            </c:forEach>
	                        	</select>
							</dd>
					</dl>
					<dl>
							<dt>支付类型：</dt>
							<dd>
								<select name="payTypeCode" id="payTypeCode">
		                            <c:forEach var="item" items="${PayTypeEnums}">
		                                <option value="${item.name }">${item.name }${item.desc }</option>
		                            </c:forEach>
	                        	</select>
							</dd>
					</dl>
					<dl>
							<dt>支付费率：</dt>
							<dd>
								<input type="text" name="payRate" maxlength="128" class="required number"/>%
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
function changeSelect(){
	$("#payTypeCode").empty();
    var payWayCode = $("#payWayCode option:selected").val();
    if(payWayCode != ""){
       $.ajax({  
            type: "GET",
            dataType : "json",
            data:{payWayCode:payWayCode},
            url: "${baseURL }/pay/way/getPayType",
            //请求成功完成后要执行的方法  
            success: function(result){
                var twoHtml = "";
                if(result.length > 0){
                   for (var i=0;i<result.length;i++){
                      var obj = result[i];
                      twoHtml += "<option value="+obj.name+">"+obj.desc+"</option>";
                   }
                   $("#payTypeCode").html(twoHtml);
                }
            },  
            error : function() {
            	alert("系统异常！");  
            }   
        });
    }
}
	
	 $("#payWayCode").change(function(){
		 changeSelect();
	    });
	 
	 changeSelect();
</script>