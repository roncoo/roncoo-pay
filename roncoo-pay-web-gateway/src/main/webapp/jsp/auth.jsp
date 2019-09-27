<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
    String path = request.getScheme() + "://" + request.getServerName()
            + ":" + request.getServerPort() + request.getContextPath()
            + "/";
    session.setAttribute("path", path);
%>
<html>
<head>
    <title>龙果支付收银台</title>
    <link href="${path}pay_files/pay.css" rel="stylesheet" type="text/css">
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

<div class="cashier_desk" style="height: 500px;">
    <div class="head clearfix" style="height: 175px;">
        <ul class="order_info">
            <li><span class="lb">鉴权单号:</span>${resultVo.orderNo}</li>
            <li><span class="lb">用户姓名：</span>${resultVo.userName}</li>
            <li><span class="lb">身份证号：</span>${resultVo.idNo}</li>
            <li><span class="lb">手机号码：</span>${resultVo.phone}</li>
            <li><span class="lb">银行卡号：</span>${resultVo.bankAccountNo}</li>
        </ul>
    </div>
    <div style="text-align: center;margin-top: 30px;">
        <c:if test="${resultVo.authStatusEnum == 'FAILED'}">
            <img id="wait" src="${path}/images/common/shibai.png"/>
            <h2 id="payMsg" style="font-size: 16px;font-weight: bold;">鉴权结果:用户信息不匹配</h2>
        </c:if>
        <c:if test="${resultVo.authStatusEnum == 'WAITING_AUTH'}">
            <img id="wait" src="${path}/images/common/shibai.png"/>
            <h2 id="payMsg"  style="font-size: 16px;font-weight: bold;">鉴权异常！请不要关闭页面，联系客服后重试!
            <a href="${path}/auth/doAuth/${resultVo.merchantNo}/${resultVo.orderNo}">
                <input type="button" value="重试" style="height: 30px;font-size:15px;width: 50px;border: none;background-color: #e10000;color: white;"/>
            </a>
            </h2>
        </c:if>
        <c:if test="${resultVo.authStatusEnum == 'SUCCESS'}">
            <img id="wait" src="${path}/images/common/chenggong.png"/>
            <h2 id="payMsg"  style="font-size: 16px;font-weight: bold;">鉴权结果:用户信息匹配</h2>
        </c:if>
        <a href="http://demo.pay.roncoo.com">
            <p class="pay_btn">
                <input type="button" value="返回首页"/>
            </p>
        </a>
    </div>
</div>

<div class="footer w100">
    <div class="container">
        <ul class="con-content">
            <li><strong class="a">联系电话：</strong></li>
            <li>吴老师：18926215592</li>
            <!-- <li class="lightly">电话繁忙时，请联系在线客服</li> -->
        </ul>
        <ul class="con-content">
            <li><strong class="b">工作时间：</strong></li>
            <li>周一至周五 09:00-23:00</li>
            <li>周六至周日 10:00-18:00</li>
        </ul>
        <ul class="con-content">
            <li><strong class="c">反馈意见：</strong></li>
            <li>service@roncoo.com</li>
        </ul>
    </div>
    <div class="copyright" id="footer">Copyright © 2015-2019 广州市领课网络科技有限公司版权所有</div>
    <p class="yue"><a href="http://www.miitbeian.gov.cn/" target="_blank">粤ICP备16009964号</a></p>
</div>
</body>
</html>
<script>
    $(function () {
        $("#footer").text("Copyright © 2015-" + new Date().getFullYear() + " 广州市领课网络科技有限公司版权所有");
    })
</script>
