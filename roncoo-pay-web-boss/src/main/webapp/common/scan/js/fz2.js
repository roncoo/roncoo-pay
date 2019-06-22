//对应second。jsp
//数据源来自cookie，展示部分信息

//Cookie操作:取
function getCookie(name) {
   var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	if(arr=document.cookie.match(reg)){
		var cookieValue = unescape(arr[2]);
		return cookieValue.replace(/(^")|("$)/g,"")
	}else{
		return null;
	}
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
		//赋展示值
//		$("#bankCode").val(bankCode);
		$("#bankAccountName").attr("value",bankAccountName);
		$("#certNo").attr("value",certNo);
		$("#bankAccountNo").attr("value",bankAccountNo);
		$("#phoneNo").attr("value",phoneNo);
	}
}

$(document).ready(function(){
	alert("fz2");
	//设置同步展示框和隐藏框数据标签
	$("#dataFlag").val("");
	//获取cookie
	getPrivateInfo();
});


