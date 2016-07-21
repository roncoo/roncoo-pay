<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getScheme() + "://" + request.getServerName()
            + ":" + request.getServerPort() + request.getContextPath()
            + "/";
    session.setAttribute("path", path);
%>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>龙果学院</title>
    <meta charset="utf-8">
    <link href="${path}pay_files/pay.css" rel="stylesheet" type="text/css">
    <style>
        .err_panel{display:table;margin:0 auto;}
        .err_panel .bd{display:table-cell;vertical-align: middle;}
        .err_panel .err_box{
            height:40px;
            padding:20px 0 0 70px;
            font-size:16px;
            font-weight:bold;
            background:url(${path}images/err.png) no-repeat left center;
        }
    </style>
</head>
<body>
<div class="wrap_header">
    <div class="header clearfix">
        <div class="logo_panel clearfix">
            <div class="logo fl"><img src="${path}pay_files/logo.png" alt=""></div>
            <div class="lg_txt">| 收银台</div>
        </div>
        <div class="fr tip_panel">
            <div class="txt">欢迎使用龙果支付付款</div>
            <a href="">常见问题</a>
        </div>
    </div>
</div>

<div class=" err_panel" style="height:300px;">
    <div class="bd">
        <div class="err_box">
            ${errorMsg}
        </div>
    </div>
</div>

<div class="footer w100">
    <div class="container">
        <ul class="con-content">
            <li><br/></li>
        </ul>
        <ul class="con-content">
        </ul>
        <ul class="con-content">
        </ul>
    </div>
    <div class="copyright">Copyright © 2015-2016 广州市领课网络科技有限公司版权所有</div>
    <p class="yue"><a href="http://www.miitbeian.gov.cn/" target="_blank">粤ICP备16009964号</a></p>
</div>

</body></html>
<html>
