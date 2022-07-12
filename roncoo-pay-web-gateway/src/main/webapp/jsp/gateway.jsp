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

<div class="cashier_desk">
    <div class="head clearfix">
        <ul class="order_info">
            <li><span class="lb">收款方：</span>${payGateWayPageShowVo.merchantName}</li>
            <li><span class="lb">订单编号：</span>${payGateWayPageShowVo.merchantOrderNo}</li>
            <li><span class="lb">商品名称：</span>${payGateWayPageShowVo.productName}</li>
        </ul>
        <div class="price fr">￥<fmt:parseNumber type="number" pattern="#,#00.0#">${payGateWayPageShowVo.orderAmount}</fmt:parseNumber></div>
    </div>
    <div class="bd">
        <ul class="sele_pay_type">
            <span class="lb">请选择支付方式：</span>
            <c:forEach items="${payGateWayPageShowVo.payTypeEnumMap}" var="payType" >
                <li>
                    <label>
                        <a href="${path}scanPay/toPay/${payGateWayPageShowVo.merchantOrderNo}/${payType.key}/${payGateWayPageShowVo.payKey}" style="font-size: 30px">
                            <c:if test="${payType.key == 'SCANPAY'}">
                                <img src="${path}images/logo2.png" alt="${payType.value.desc}" />
                            </c:if>
                            <c:if test="${payType.key == 'DIRECT_PAY'}">
                                <img src="${path}images/logo.png" alt="${payType.value.desc}" />
                            </c:if>
                            <c:if test="${payType.key == 'HUA_BEI_FEN_QI_PAY'}">
                                <img src="${path}images/logo_hbfq.png" alt="${payType.value.desc}" />
                            </c:if>
                        </a>
                    </label>
                </li>
            </c:forEach>
        </ul>
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
    <div class="copyright" id="footer">Copyright © 2015-2019 广州市领课网络科技有限公司版权所有</div>
    <p class="yue"><a href="http://www.miitbeian.gov.cn/" target="_blank">粤ICP备16009964号</a></p>
</div>
</body>
</html>
<script>
    $(function () {
        $("#footer").text("Copyright © 2015-"+new Date().getFullYear()+" 广州市领课网络科技有限公司版权所有")
    })
</script>
