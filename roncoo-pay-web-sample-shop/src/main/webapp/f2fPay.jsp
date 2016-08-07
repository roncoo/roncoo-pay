<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getScheme() + "://" + request.getServerName()
          + ":" + request.getServerPort() + request.getContextPath()
          + "/";
  session.setAttribute("path", path);
%>
<html>
<head>
    <title>条码支付</title>
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
      <li><img src="${path}images/pay.png" alt="支付宝支付" />
        <form action="${path}roncooPay/f2fPay" method="post">
          <label>请输入支付宝支付授权码:</label><input type="text" size="30" name="authCode"/><br />
          <input type="hidden" name="productName" value="支付宝支付产品测试" /><br />
          <input type="hidden" name="orderPrice" value="0.1" /><br />
          <input type="hidden" name="payWayCode" value="ALIPAY">
          <input type="hidden" name="remark" value="支付宝支付备注信息" /><br />
          <p class="pay_btn">
            <input type="submit" value="0.1元支付体验" />
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
      <li>陈老师：18027263931</li>
      <!-- <li class="lightly">电话繁忙时，请联系在线客服</li> -->
    </ul>
    <ul class="con-content">
      <li><strong class="b">工作时间：</strong></li>
      <li>周一至周五  09:00-23:00</li>
      <li>周六至周日  10:00-18:00</li>
    </ul>
    <ul class="con-content">
      <li><strong class="c">反馈意见：</strong></li>
      <li>service@roncoo.com</li>
    </ul>
  </div>
  <div class="copyright">Copyright © 2015-2016 广州市领课网络科技有限公司版权所有</div>
  <p class="yue"><a href="http://www.miitbeian.gov.cn/" target="_blank">粤ICP备16009964号</a></p>
</div>
</body>

</html>
