webpackJsonp([21],{

/***/ 0:
/***/ function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__(55);


/***/ },

/***/ 55:
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(2);
	var api = __webpack_require__(3);
	var msgs = __webpack_require__(16);
	
	if($(".header-r .user_box").length <= 0){
		msgs.tip("登陆之后才可以支付订单哦");
	    location.href = "/index.html";
	}
	
	var element = document.getElementById("ewm");
	var aliPayStatus = $(".pay_success").attr("pay-status");
	var clearV;
	if (aliPayStatus == "") {
		api.req('getPay',{oid:$("#ewm").attr("oid"),type:"pay"},function(body){
			if (body.code == 0) {
			    var qr = qrcode(10, 'M'); 
			    qr.addData(body.data);
			    qr.make(); // 生成二维码
			    element.innerHTML = qr.createImgTag(); // 插入二维码
			    
			    // 发起轮询，每秒执行一次
			    clearV = setInterval(function(){
			    	api.req('getPay',{oid:$("#ewm").attr("oid"),type:"order"},function(rnt){
			    		if (rnt.data.status != "0") {
			    			console.log(rnt);
			    			$("#box").addClass("pay_ok");
			    			if (!!rnt.cid) {
				    			$('#rightnow').attr('href','/details?cid=' + rnt.cid);
				    		}else{
				    			$('#rightnow').attr('href','/index');
				    		};
			    			clearInterval(clearV);
			    		};
			    	})
			    },1000);
			    
			}else{
				msgs.tip("获取支付信息失败")
			};
		})
	}else{
		$('#rightnow').attr('href','/account/course.html');
	};

/***/ }

});
//# sourceMappingURL=pay.js.map