<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../common/taglib/taglib.jsp"%>
<div class="pageContent">
	<form action="${baseURL }/pay/config/add" cssClass="pageForm required-validate"
        onsubmit="return validateCallback(this, navTabAjaxDone);" method="post" >
        <input type="hidden" name="navTabId" value="yhzfpz">
        <input type="hidden" name="callbackType" value="closeCurrent">
        <input type="hidden" name="forwardUrl" value="">
        <div class="tabsContent pageFormContent"  layoutH="56">
			<div>
				<fieldset>
					<legend>用户支付配置</legend>
                    <dl>
							<dt>用户编号：</dt>
							<dd>
								<input type="text" name="user.userNo" size="25" maxlength="128" class="required readonly" />
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
							<dt>支付产品编号：</dt>
							<dd>
								<input type="text" name="product.productCode" id="productCode" size="25"  maxlength="128" class="required readonly"/>
								<a class="btnLook" href="${baseURL}/pay/product/lookupList" lookupGroup="product" callback="product_callback">搜索</a>
							</dd>
					</dl>
					<dl>
							<dt>支付产品名称：</dt>
							<dd>
								<input type="text" name="product.productName" maxlength="80" class="required readonly" />
							</dd>
					</dl>
					<dl>
							<dt>收款方式：</dt>
							<dd>
								<select name="fundIntoType" id="fundIntoType">
		                            <c:forEach var="item" items="${FundInfoTypeEnums}">
		                                <option value="${item.name }">${item.desc }</option>
		                            </c:forEach>
	                        	</select>
							</dd>
					</dl>
					<dl>
							<dt>风险预存期：</dt>
							<dd>
								<input type="text" name="riskDay" maxlength="10" class="required digits"/>
							</dd>
					</dl>
					<dl>
							<dt>是否自动结算：</dt>
							<dd>
								<input type="radio" name="isAutoSett" value="YES"/>是
								<input type="radio" name="isAutoSett" value="NO" checked="checked"/>否
							</dd>
					</dl>
				</fieldset>
				
				<fieldset id="we_field" style="display: none;">
					<legend>微信设置</legend>
					<table style="border-spacing: 10px">
						<tr>
							<td>APPID：</td>
							<td><input type="text" name="we_appId" id="we_appId" maxlength="128" class="required" />&nbsp;&nbsp;请到“<a href="https://mp.weixin.qq.com/" target="_blank" style="color: blue;">微信公众平台</a>”上点击“开发者中心”，复制AppID，例如：wx0fe958a123233135</span></td>
						</tr>
						<tr>
							<td>商户密钥：</td>
							<td><input type="text" name="we_partnerKey" id="we_partnerKey" maxlength="200" class="required" />&nbsp;&nbsp;请到“<a href="https://pay.weixin.qq.com/" target="_blank" style="color: blue;">微信支付商户平台</a>”点击 帐户设置→API安全→设置密钥（请根据提示安装财付通证书），32位秘钥</td>
						</tr>
						<tr>
							<td>商户号：</td>
							<td><input type="text" name="we_merchantId" id="we_merchantId" maxlength="150" class="required" />&nbsp;&nbsp;微信支付开通成功的通知邮件里，复制“微信支付商户号”，例如：10054321</td>
						</tr>
					</table>
				</fieldset>
				
				<fieldset id="ali_field" style="display: none;">
					<legend>支付宝设置</legend>
					<table style="border-spacing: 10px">
						<tr>
							<td>合作者身份ID：</td>
							<td><input type="text" name="ali_partner" id="ali_partner" maxlength="128" class="required" /></td>
						</tr>
						<tr>
							<td>MD5_KEY：</td>
							<td><input type="text" name="ali_key" id="ali_key" maxlength="150" class="required" /></td>
						</tr>
						<tr>
							<td>收款账号：</td>
							<td><input type="text" name="ali_sellerId" id="ali_sellerId" maxlength="200" class="required" /></td>
						</tr>
						<tr>
							<td>APPID：</td>
							<td><input type="text" name="ali_appid" id="ali_appid" maxlength="200" class="required" /></td>
						</tr>
						<tr>
							<td>支付宝私钥：</td>
							<td><input type="text" name="ali_rsaPrivateKey" id="ali_rsaPrivateKey" maxlength="200" class="required" /></td>
						</tr>
						<tr>
							<td>支付宝公钥：</td>
							<td><input type="text" name="ali_rsaPublicKey" id="ali_rsaPublicKey" maxlength="200" class="required" /></td>
						</tr>
					</table>
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

	function changeWePay(){
		var fundIntoType = $("#fundIntoType").val();
		var productCode = $("#productCode").val();
		$("#we_field").hide();
		$("#we_appId").attr("class", "");
		$("#we_merchantId").attr("class", "");
		$("#we_partnerKey").attr("class", "");
		
		$("#ali_field").hide();
		$("#ali_partner").attr("class", "");
		$("#ali_sellerId").attr("class", "");
		$("#ali_key").attr("class", "");
		$("#ali_appid").attr("class", "");
		$("#ali_rsaPrivateKey").attr("class", "");
		$("#ali_rsaPublicKey").attr("class", "");
		if(fundIntoType == 'MERCHANT_RECEIVES'){
			if(productCode != ""){
				$.ajax({  
		            type: "GET",
		            dataType : "json",
		            data:{productCode : productCode},
		            url: "${baseURL }/pay/way/getPayWay",
		            //请求成功完成后要执行的方法  
		            success: function(result){
		                if(result.length > 0){
		                   for (var i=0;i<result.length;i++){
		                      var obj = result[i];
		                      if(obj.name == "WEIXIN"){
		                      	$("#we_field").show();
		              			$("#we_appId").attr("class", "required");
		              			$("#we_merchantId").attr("class", "required");
		              			$("#we_partnerKey").attr("class", "required");
		              			
		                      }else if(obj.name == "ALIPAY"){
		                  		$("#ali_field").show();
		              			$("#ali_partner").attr("class", "required");
		              			$("#ali_sellerId").attr("class", "required");
		              			$("#ali_key").attr("class", "required");
		              			$("#ali_appid").attr("class", "required");
		              			$("#ali_rsaPrivateKey").attr("class", "required");
		              			$("#ali_rsaPublicKey").attr("class", "required");
		                      }
		                   }
		                }
		            },  
		            error : function() {
		            	alert("系统异常！");  
		            }   
		        });
			}
			
		}
	}	 
	
	changeWePay();
	
	$("#fundIntoType").change(function(){
		changeWePay();
	    });
	
	function product_callback()
	{
		changeWePay();
	}
</script>