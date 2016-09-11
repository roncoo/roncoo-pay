<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <%@include file="../../common/taglib.jsp"%>
  <%@include file="../../common/lte.jsp"%>
  <title>龙果支付</title>
</head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />

<body class="hold-transition login-page">
<div class="login-box">
  <div class="login-logo">
    <img src="${baseURL }/lte/images/logo2.png" />
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body">
    <p class="login-box-msg">商户后台登录</p>

    <form action="${baseURL }/index" method="post">
      <div class="form-group has-feedback">
        <input type="text" class="form-control" placeholder="手机号" name="mobile" value="${mobile }"/>
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" class="form-control" placeholder="密 码" name="password" value="${password }"/>
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback" style="display: none;" id="login_msg">
        <span style="color: red;" id="msg">${msg }</span> 
      </div>
      <div class="row">
        
        <!-- /.col -->
        <div class="col-xs-12">
          <button type="submit" class="btn btn-primary btn-block btn-flat">登 录</button>
        </div>
        <!-- /.col -->
      </div>
    </form>
	
    

  </div>
  <!-- /.login-box-body -->
</div>
<script>
var msg = '${msg}';
if(msg != ''){
	$("#login_msg").show();
}else{
	$("#login_msg").hide();
}
  $(function () {
    $('input').iCheck({
      checkboxClass: 'icheckbox_square-blue',
      radioClass: 'iradio_square-blue',
      increaseArea: '20%' // optional
    });
    
    
  });
</script>
</body>
</html>