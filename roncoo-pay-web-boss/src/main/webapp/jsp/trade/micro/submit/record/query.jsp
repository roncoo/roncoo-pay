<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../../../../common/taglib/taglib.jsp" %>
<script type="text/javascript" src="${baseURL }/common/scan/js/jquery.qrcode.min.js"></script>
<div class="pageContent">
    <div class="pageFormContent">
        <div class="weixin" id="weixinDiv">
            <h3>查询信息</h3><br>
            <h5>${returnMap }</h5><br>
            <c:choose>
                <c:when test="${! empty sign_url}">
                    <h3>扫一扫进行绑定(联系人微信)</h3>
                </c:when>
                <c:otherwise>
                    <h3>获取二维码失败</h3>
                </c:otherwise>
            </c:choose>
            <br>
            <div class="x-left">
                <div>
                    <div class="er" id="code" oid="4835a85a4e01402aa17f8a73c356f80d"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="formBar">
        <ul>
            <li>
                <div class="button">
                    <div class="buttonContent">
                        <button type="button" class="close">取消</button>
                    </div>
                </div>
            </li>
        </ul>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        $('#code').qrcode("${sign_url}");
    })
</script>
