<%--
  Created by IntelliJ IDEA.
  User: LYQ
  Date: 2017/12/14
  Time: 10:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试</title>
    <script type="text/javascript" src="${path}js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
</head>
<body>

</body>
<script>
    var appId = "wxdc4e0888857e858d";
    var nonceStr = "2858338391442441211798337075320";
    var package = "prepay_id=wx20171214105006b93f3bb9e90854903185";
    var paySign = "BAF449D858CFD0CE6D488146DEF41A64";
    var timeStamp = "1513219805815";

    window.onload = function onBridgeReady(){

        wx.requestPayment({
            'timeStamp': "1395712654",
            'nonceStr': "e61463f8efa94090b1f366cccfbbb444",
            'package': "prepay_id=u802345jgfjsdfgsdg888",
            'signType': "MD5",
            'paySign': "70EA570631E4BB79628FBCA90534C63FF7FADD89",
            'success':function(res){
                resolve(res)
            },
            'fail':function(res){
                reject(res)
            }
        })


    }

</script>
</html>
