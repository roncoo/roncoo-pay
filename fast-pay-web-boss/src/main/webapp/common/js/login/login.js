/**
 * 登录验证码.
 */
function changeRandomCode(obj){
	//获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    //获取带"/"的项目名，如：/uimcardprj
    projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	//获取当前的时间作为参数，无具体意义
	var timenow = new Date().getTime();
	//每次请求需要一个不同的参数，否则可能会返回同样的验证码
	//这和浏览器的缓存机制有关系，也可以把页面设置为不缓存，这样就不用这个参数了
	obj.src = projectName+"/randomCode/create/" + timenow;
}

