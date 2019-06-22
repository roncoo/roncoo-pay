//对应first。jsp
//提交前同步手动数据
function storeInfo(){
	//判断数据来源,手动输入信息要同步展示数据和隐藏数据，cookie获取信息不同步展示数据和隐藏数据
	if($("#dataFlag").val()!="cookie"){
		synDate();
	}
	return true;
 }

//同步展示数据和隐藏数据
function synDate(){
	var bankCode=$.trim($("#bankCode").val());
	var bankAccountName=$.trim($("#bankAccountName").val());
	var certNo=$.trim($("#certNo").val());
	var bankAccountNo=$.trim($("#bankAccountNo").val());
	var phoneNo=$.trim($("#phoneNo").val());
	
	//缺少值时，可以同步
	//同步输入字段到隐藏字段，以便过js校验
	$("#bankAccountNameHidden").val(bankAccountName);
	$("#certNoHidden").val(certNo);
	$("#bankAccountNoHidden").val(bankAccountNo);
	$("#phoneNoHidden").val(phoneNo);
	
	
	//缺少值不提交cookie
	if(bankCode.length>0 && bankAccountName.length>0 && certNo.length>0 && bankAccountNo.length>0 && phoneNo.length>0 ){
		setCookie("bankCode",bankCode);
		
		
		//形成伪展示值
		var bankAccountNameShow = bankAccountName.substring(0,1)+"*";
		var certNoShow = certNo.substring(0,9)+"****"+certNo.substring(14,18);
		var len=bankAccountNo.length;
		var bankAccountNoShow = bankAccountNo.substring(0,8)+"****"+bankAccountNo.substring(len-4,len);
		var phoneNoShow = phoneNo.substring(0,3)+"****"+phoneNo.substring(7,11)
		setCookie("bankAccountName",bankAccountNameShow);
		setCookie("certNo",certNoShow);
		setCookie("bankAccountNo",bankAccountNoShow);
		setCookie("phoneNo",phoneNoShow);
		//加密信息
		var infoStr="bankCode="+bankCode+"&bankAccountName="+bankAccountName+"&certNo="+certNo+"&bankAccountNo="+bankAccountNo+"&phoneNo="+phoneNo;
		var info=encodeURIComponent(infoStr);
		setCookie("info",info);
	}
}

//Cookie操作
function getCookie(name) {
   var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	if(arr=document.cookie.match(reg)){
		var cookieValue = unescape(arr[2]);
		return cookieValue.replace(/(^")|("$)/g,"")
	}else{
		return null;
	}
}
function setCookie(name,value)
{
   var Days = 365;
   var exp  = new Date();    //new Date("December 31, 9998");
       exp.setTime(exp.getTime() + Days*24*60*60*1000);//一年内
       document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}

//获取最近一次输入个人信息（银行卡号）
function getPrivateInfo(){
	var bankCode = getCookie('bankCode');
	var bankAccountName = getCookie('bankAccountName');
	var certNo = getCookie('certNo');
	var bankAccountNo = getCookie('bankAccountNo');
	var phoneNo = getCookie('phoneNo');
	var info = getCookie('info');
	//不接受残缺cookie
	if(bankCode!=null && bankAccountName!=null && certNo!=null && bankAccountNo!=null && phoneNo!=null && info!=null){
		//cookie数据，不同步
		$("#dataFlag").val("cookie");
		//赋展示值
		$("#bankCode").val(bankCode);
		$("#bankAccountName").attr("value",bankAccountName);
		$("#certNo").attr("value",certNo);
		$("#bankAccountNo").attr("value",bankAccountNo);
		$("#phoneNo").attr("value",phoneNo);
		//赋值给隐藏字段
		var infoStr=decodeURIComponent(info);
		var arr=infoStr.split("&");
		for( var i=0;i<arr.length;i++){
			var arr1=arr[i].split("=");
			var name=arr1[0];
			var val=arr1[1];
			$("#"+name+"Hidden").val(val);
		}
	}
}

//输入框绑定变化事件
function onchangeFun(){
	$("select.npt,input.npt").on("change",function(){
		if($("#dataFlag").val()=="cookie"){//非手写，增加变化事件监控,清除默认数据
			//给出数据清零提示
			tips();
			var tempVal=$(this).val();
			//重置数据
			$("select.npt,input.npt").val("");
			$("#bankCode").get(0).selectedIndex = 0;
			//保存当前框
			$(this).val(tempVal);
			//手写数据,变化事件失效
			$("#dataFlag").val("");
		}
	})
}

function tips(){
	var msg = "输入新的账号信息？请确认！"; 
	 if (confirm(msg)==true){ 
		 return true; 
	 }else{ 
		 return false; 
	 } 
}

$(document).ready(function(){
	alert("fz1");
	//设置同步展示框和隐藏框数据标签
	$("#dataFlag").val("");//手动，需同步
	//获取cookie
	getPrivateInfo();
	//采用cookie值,批量给输入框增加变化事件，一旦变化，数据源改为非cookie
	if($("#dataFlag").val()=="cookie"){
		onchangeFun();
	}
});


