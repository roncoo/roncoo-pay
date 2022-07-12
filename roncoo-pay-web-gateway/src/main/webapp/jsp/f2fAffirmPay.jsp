<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String path = request.getScheme() + "://" + request.getServerName()
            + ":" + request.getServerPort() + request.getContextPath()
            + "/";
    session.setAttribute("path", path);
%>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="${path}js/jquery-1.7.2.min.js"></script>
</head>
<body>
<div style="width: 520px;margin: auto;margin-top: 150px;height: 360px;text-align: center;">
    <img id="wait" src="${path}/images/jiazai.gif"/>
    <h2 id="payMsg">支付扣款中......</h2>
</div>
<h1 hidden>支付成功</h1>
</body>
<script>

    var queryObj = setInterval(function () {
        queryOrder()
    }, 500)

    function queryOrder() {
        var trxNo = "${result.trxNo}";
        if (trxNo == null || trxNo == "") {
            $("#wait").attr("src", "${path}/images/err.png");
            $("#payMsg").text("订单["+result.bankOrderNo+"]，订单号能为空!");
            clearInterval(queryObj);
            return;
        }
        var queryUrl = "${path}f2fPay/order/query";
        $.ajax({
            type: 'POST',
            url: queryUrl,
            data: 'trxNo=' + trxNo,
            dataType: 'json',
            async: false,
            success: function (result) {
                console.log(result);
                if (result != null) {
                    if ("SUCCESS" == result.status) {
                        isOver = true;
                        clearInterval(queryObj);
                        $("#wait").attr("src", "${path}/images/chenggong.png");
                        $("#payMsg").text("订单["+result.bankOrderNo+"]，支付成功!");
                    }else if("FAILED" == result.status){
                        isOver = true;
                        clearInterval(queryObj);
                        $("#wait").attr("src", "${path}/images/err.png");
                        $("#payMsg").text("订单["+result.bankOrderNo+"]，支付失败!");
                    }
                }
            },
            error: function (result) {
                console.log("请求失败！");
            }
        });
    }

</script>
</html>

