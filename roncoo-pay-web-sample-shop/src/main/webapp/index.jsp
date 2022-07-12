<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getScheme() + "://" + request.getServerName()
            + ":" + request.getServerPort() + request.getContextPath()
            + "/";
    session.setAttribute("path", path);
%>
<html>
<head>
    <title>扫码支付</title>
    <script type="text/javascript" src="${path}js/jquery-1.7.2.min.js"></script>
    <link href="${path}pay_files/pay.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="wrap_header">
    <div class="header clearfix">
        <div class="logo_panel clearfix">
            <div class="logo fl"><img src="${path}pay_files/logo.png" alt="logo"></div>
            <div class="lg_txt">| 龙果支付体验平台</div>
        </div>
        <div class="fr tip_panel">
            <div class="txt">欢迎使用龙果支付付款</div>
            <a href="">常见问题</a>
        </div>
    </div>
</div>

<div class="cashier_desk pay_ment">
    <div class="bd">
        <ul class="payment">
            <li style="margin-left: 20px;">
                <img src="${path}images/weixin.png" alt="微信支付"/>
                <form action="${path}roncooPay/scanPay" method="post">
                    <input type="hidden" name="productName" value="微信支付产品测试"/><br/>
                    <input type="hidden" name="orderPrice" value="0.1"/><br/>
                    <input type="hidden" name="payType" value="SCANPAY">
                    <input type="hidden" name="remark" value="微信支付备注信息"/>
                    <p class="pay_btn">
                        <input type="submit" value="0.1元支付体验"/>
                    </p>
                </form>
            </li>
            <li style="margin-left: 20px;"><img src="${path}images/zhifubao.png" alt="支付宝支付"/>
                <form action="${path}roncooPay/scanPay" method="post">
                    <input type="hidden" name="productName" value="支付宝支付产品测试"/><br/>
                    <input type="hidden" name="orderPrice" value="0.1"/><br/>
                    <input type="hidden" name="payType" value="DIRECT_PAY">
                    <input type="hidden" name="remark" value="支付宝支付备注信息"/>
                    <p class="pay_btn">
                        <input type="submit" value="0.1元支付体验"/>
                    </p>
                </form>
            </li>
            <li style="margin-left: 20px;"><img src="${path}images/hbfq.png" alt="支付宝花呗分期支付"/>
                <form action="${path}roncooPay/scanPay" method="post">
                    <input type="hidden" name="productName" value="支付宝支付产品测试"/><br/>
                    <input type="hidden" name="orderPrice" value="0.1"/><br/>
                    <input type="hidden" name="payType" value="HUA_BEI_FEN_QI_PAY">
                    <input type="hidden" name="numberOfStages" value="3">
                    <input type="hidden" name="remark" value="支付宝支付备注信息"/>
                    <p class="pay_btn">
                        <input type="submit" value="0.1元支付花呗分期体验"/>
                    </p>
                </form>
            </li>
            <li style="margin-left: 20px;">
                <img src="${path}images/wangguan.png" alt="网关支付"/>
                <form action="${path}roncooPay/scanPay" method="post">
                    <input type="hidden" name="productName" value="网关支付产品测试"/><br/>
                    <input type="hidden" name="orderPrice" value="0.1"/><br/>
                    <input type="hidden" name="payType" value="">
                    <input type="hidden" name="remark" value="网关支付备注信息"/>
                    <p class="pay_btn">
                        <input type="submit" value="0.1元支付体验"/>
                    </p>
                </form>
            </li>
            <%--<li style="float: left;margin-left: 60px;margin-top: 20px;">
                <img src="${path}images/jianquan.png" alt="鉴权支付"/>
                <a href="${path}auth/init">
                    <p class="pay_btn">
                        <input type="button" value="0.5元鉴权体验"/>
                    </p>
                </a>
            </li>

            <li style="float: left;margin-left: 60px;margin-top: 20px;">
                <img width="344px;" src="${path}images/wx_xiaochengxu.jpg" alt="微信小程序"/>
                <p style="font-size: 20px; margin-top: 15px;font-weight: bold">
                    小程序演示
                </p>
            </li>--%>

        </ul>
        <ul class="payment">
            <li style="margin-left: 20px;">
                <img src="${path}images/beisao.png" alt="条码支付"/>
                <form action="${path}roncooPay/toF2FPay" method="post">
                    <br/><br/>
                    <p class="pay_btn">
                        <input type="submit" value="条码支付体验"/>
                    </p>
                </form>
            </li>
        </ul>
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
