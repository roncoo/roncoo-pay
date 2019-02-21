<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../../../../common/taglib/taglib.jsp" %>
<div class="pageContent">
    <form action="${baseURL }/trade/micro/submit/record/add" method="post" enctype="multipart/form-data" onsubmit="return validateCallback(this, navTabAjaxDone);">
        <input type="hidden" name="navTabId" value="jjjlgl">
        <input type="hidden" name="callbackType" value="closeCurrent">
        <input type="hidden" name="forwardUrl" value="">
        <div class="tabsContent pageFormContent" layoutH="58">
            <div>
                <fieldset>
                    <legend>店铺信息</legend>
                    <dl>
                        <dt>门店名称：</dt>
                        <dd>
                            <input type="text" name="storeName" maxlength="50" value="${storeName}" class="required"/>
                        </dd>
                    </dl>
                    <dl>
                        <dt>商户简称：</dt>
                        <dd>
                            <input type="text" name="merchantShortname" placeholder="将在支付完成页向买家展示" maxlength="30" class="required"/>
                        </dd>
                    </dl>
                    <dl>
                        <dt>客服电话：</dt>
                        <dd>
                            <input type="text" name="servicePhone" placeholder="将在交易记录中向买家展示" maxlength="20" class="required"/>
                        </dd>
                    </dl>
                    <div style="max-width: 800px;">
                        <dl>
                            <dt>服务描述：</dt>
                            <dd>
                                <select name="productDesc">
                                    <option value="餐饮">餐饮</option>
                                    <option value="线下零售">线下零售</option>
                                    <option value="居民生活服务">居民生活服务</option>
                                    <option value="休闲娱乐">休闲娱乐</option>
                                    <option value="交通出行">交通出行</option>
                                    <option value="其他">其他</option>
                                </select>
                            </dd>
                        </dl>
                        <dl>
                            <dt>费率：</dt>
                            <dd>
                                <select name="rate">
                                    <option value="0.6%">0.6%</option>
                                    <option value="0.59%">0.59%</option>
                                    <option value="0.58%">0.58%</option>
                                    <option value="0.55%">0.55%</option>
                                    <option value="0.5%">0.5%</option>
                                    <option value="0.49%">0.49%</option>
                                    <option value="0.48%">0.48%</option>
                                    <option value="0.45%">0.45%</option>
                                    <option value="0.4%">0.4%</option>
                                    <option value="0.39%">0.39%</option>
                                    <option value="0.38%">0.38%</option>
                                </select>
                            </dd>
                        </dl>
                        <dl>
                            <dt>门店省市编码：</dt>
                            <dd>
                                <select name="storeAddressCode" id="storeAddressCode">
                                    <c:forEach var="item" items="${wxCityNoList}">
                                        <option value="${item.name}">${item.desc}</option>
                                    </c:forEach>
                                </select>
                            </dd>
                        </dl>
                        <dl>
                            <dt>门店街道名称：</dt>
                            <dd>
                                <input type="text" name="storeStreet" maxlength="300" class="required"/>
                            </dd>
                        </dl>
                    </div>
                    <dl class="nowrap">
                        <dt>门店门口照片：</dt>
                        <dd>
                            <button type="button" id="storeEntranceButton" class="btn btn-primary">上传</button>
                            <input type="file" id="storeEntranceFile" name="storeEntranceFile" accept="image/*" style="background-color: #9bd3ec">
                            <input type="hidden" id="storeEntrancePic" name="storeEntrancePic">
                        </dd>
                    </dl>
                    <dl class="nowrap">
                        <dt>店内环境照片：</dt>
                        <dd>
                            <button type="button" id="indoorPicButton" class="btn btn-primary">上传</button>
                            <input type="file" id="indoorPicFile" name="indoorPicFile" accept="image/*" style="background-color: #9bd3ec">
                            <input type="hidden" id="indoorPic" name="indoorPic">
                        </dd>
                    </dl>
                </fieldset>
                <fieldset>
                    <legend>收款信息</legend>
                    <dl>
                        <dt>开户银行：</dt>
                        <dd>
                            <select name="accountBank" id="accountBank">
                                <option value="工商银行">工商银行</option>
                                <option value="交通银行">交通银行</option>
                                <option value="招商银行">招商银行</option>
                                <option value="民生银行">民生银行</option>
                                <option value="中信银行">中信银行</option>
                                <option value="浦发银行">浦发银行</option>
                                <option value="兴业银行">兴业银行</option>
                                <option value="光大银行">光大银行</option>
                                <option value="广发银行">广发银行</option>
                                <option value="平安银行">平安银行</option>
                                <option value="北京银行">北京银行</option>
                                <option value="华夏银行">华夏银行</option>
                                <option value="农业银行">农业银行</option>
                                <option value="建设银行">建设银行</option>
                                <option value="邮政储蓄银行">邮政银行</option>
                                <option value="中国银行">中国银行</option>
                                <option value="宁波银行">宁波银行</option>
                                <option value="其他银行">其他银行</option>
                            </select>
                        </dd>
                    </dl>
                    <dl>
                        <dt>银行省市编码：</dt>
                        <dd>
                            <select name="bankAddressCode" id="bankAddressCode">
                                <c:forEach var="item" items="${wxCityNoList}">
                                    <option value="${item.name}">${item.desc}</option>
                                </c:forEach>
                            </select>
                        </dd>
                    </dl>
                    <dl>
                        <dt>银行卡号：</dt>
                        <dd>
                            <input type="text" name="accountNumber" maxlength="50" class="required"/>
                        </dd>
                    </dl>
                    <div style="max-width: 800px;">
                        <dl>
                            <dt>卡号姓名：</dt>
                            <dd>
                                <input type="text" name="idCardName" maxlength="64" class="required"/>
                            </dd>
                        </dl>
                        <dl>
                            <dt>卡号手机：</dt>
                            <dd>
                                <input type="text" id="contactPhone" name="contactPhone" maxlength="11" class="required"/>
                            </dd>
                        </dl>
                        <dl>
                            <dt>身份证号码：</dt>
                            <dd>
                                <input type="text" id="idCardNumber" name="idCardNumber" maxlength="18" class="required"/>
                            </dd>
                        </dl>
                        <dl>
                            <dt>身份证有效期限：</dt>
                            <dd>
                                <input type="text" size="8" id="idCardValidTimeBegin" name="idCardValidTimeBegin" class="date textInput readonly" readonly="true">
                                <input type="text" size="8" id="idCardValidTimeEnd" name="idCardValidTimeEnd" class="date textInput readonly" readonly="true">
                            </dd>
                        </dl>
                    </div>
                    <dl class="nowrap">
                        <dt>身份证人像面：</dt>
                        <dd>
                            <button type="button" id="idCardCopyButton" class="btn btn-primary">上传</button>
                            <input type="file" id="idCardCopyFile" name="idCardCopyFile" accept="image/*" style="background-color: #9bd3ec">
                            <input type="hidden" id="idCardCopy" name="idCardCopy">
                        </dd>
                    </dl>
                    <dl class="nowrap">
                        <dt>身份证国徽面：</dt>
                        <dd>
                            <button type="button" id="idCardNationalButton" class="btn btn-primary">上传</button>
                            <input type="file" id="idCardNationalFile" name="idCardNationalFile" accept="image/*" style="background-color: #9bd3ec">
                            <input type="hidden" id="idCardNational" name="idCardNational">
                        </dd>
                    </dl>
                </fieldset>
            </div>
        </div>
        <div class="formBar">
            <ul>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button type="submit" id="submitBtn">提交</button>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="button">
                        <div class="buttonContent">
                            <button type="button" class="close">取消</button>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </form>
</div>

<script>
    //查询
    $(function () {
        $("#storeAddressCode,#bankAddressCode,#accountBank").select2({
            width: '160px'
        });
    })

    //上传门店门口图片
    $("#storeEntranceButton").on("click", function () {
        uploadStoreEntranceFile();
    });

    function uploadStoreEntranceFile() {
        var storeEntranceFile = $("#storeEntranceFile").get(0).files[0];
        if (storeEntranceFile == null || storeEntranceFile == "") {
            return alert("请选择文件");
        }
        var formData = new FormData();
        formData.append("file", storeEntranceFile);
        $.ajax({
            type: "POST",
            url: "${staticBase }trade/micro/submit/record/uploadImage",
            data: formData,
            async: false,
            processData: false, //必须false才会自动加上正确的Content-Type
            dataType: 'json',
            contentType: false,//必须false才会避开jQuery对 formdata 的默认处理
            success: function (data) {
                if ("OK" == data.return_msg) {
                    $("#storeEntrancePic").val(data.media_id);
                    alert("上传成功");
                } else {
                    alert(data.return_msg);
                }
            },
            error: function (xhr, textStatus) {
                ShowFailure(res)
            }
        });
    }

    //上传店内环境照片
    $("#indoorPicButton").on("click", function () {
        uploadIndoorPicFile();
    });

    function uploadIndoorPicFile() {
        var indoorPicFile = $("#indoorPicFile").get(0).files[0];
        if (indoorPicFile == null || indoorPicFile == "") {
            return alert("请选择文件");
        }
        var formData = new FormData();
        formData.append("file", indoorPicFile);
        $.ajax({
            type: "POST",
            url: "${staticBase }trade/micro/submit/record/uploadImage",
            data: formData,
            async: false,
            processData: false, //必须false才会自动加上正确的Content-Type
            dataType: 'json',
            contentType: false,//必须false才会避开jQuery对 formdata 的默认处理
            success: function (data) {
                if ("OK" == data.return_msg) {
                    $("#indoorPic").val(data.media_id);
                    alert("上传成功");
                } else {
                    alert(data.return_msg);
                }
            },
            error: function (xhr, textStatus) {
                ShowFailure(res)
            }
        });
    }

    //上传身份证人像面
    $("#idCardCopyButton").on("click", function () {
        uploadIdCardCopyFile();
    });

    function uploadIdCardCopyFile() {
        var idCardCopyFile = $("#idCardCopyFile").get(0).files[0];
        if (idCardCopyFile == null || idCardCopyFile == "") {
            return alert("请选择文件");
        }
        var formData = new FormData();
        formData.append("file", idCardCopyFile);
        $.ajax({
            type: "POST",
            url: "${staticBase }trade/micro/submit/record/uploadImage",
            data: formData,
            async: false,
            processData: false, //必须false才会自动加上正确的Content-Type
            dataType: 'json',
            contentType: false,//必须false才会避开jQuery对 formdata 的默认处理
            success: function (data) {
                if ("OK" == data.return_msg) {
                    $("#idCardCopy").val(data.media_id);
                    alert("上传成功");
                } else {
                    alert(data.return_msg);
                }
            },
            error: function (xhr, textStatus) {
                ShowFailure(res)
            }
        });
    }

    //上传身份证国徽面
    $("#idCardNationalButton").on("click", function () {
        uploadIdCardNationalFile();
    });

    function uploadIdCardNationalFile() {
        var idCardNationalFile = $("#idCardNationalFile").get(0).files[0];
        if (idCardNationalFile == null || idCardNationalFile == "") {
            return alert("请选择文件");
        }
        var formData = new FormData();
        formData.append("file", idCardNationalFile);
        $.ajax({
            type: "POST",
            url: "${staticBase }trade/micro/submit/record/uploadImage",
            data: formData,
            async: false,
            processData: false, //必须false才会自动加上正确的Content-Type
            dataType: 'json',
            contentType: false,//必须false才会避开jQuery对 formdata 的默认处理
            success: function (data) {
                if ("OK" == data.return_msg) {
                    $("#idCardNational").val(data.media_id);
                    alert("上传成功");
                } else {
                    alert(data.return_msg);
                }
            },
            error: function (xhr, textStatus) {
                ShowFailure(res)
            }
        });
    }

    // 提交校验
    $("#submitBtn").on("click", function () {
        var storeEntrancePic = $("#storeEntrancePic").val();
        var indoorPic = $("#indoorPic").val();
        var contactPhone = $("#contactPhone").val();
        var idCardNumber = $("#idCardNumber").val();
        var idCardValidTimeBegin = $("#idCardValidTimeBegin").val();
        var idCardValidTimeEnd = $("#idCardValidTimeEnd").val();
        var idCardCopy = $("#idCardCopy").val();
        var idCardNational = $("#idCardNational").val();
        if (storeEntrancePic == "") {
            alert("请选择并上传门店门口照片");
            return false;
        }
        if (indoorPic == "") {
            alert("请选择并上传店内环境照片");
            return false;
        }
        if (!(/^1[3456789]\d{9}$/.test(contactPhone))) {
            alert("卡号手机错误");
            return false;
        }
        if (/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(idCardNumber) === false) {
            alert("身份证号码错误");
            return false;
        }
        if (idCardValidTimeBegin == "") {
            alert("请选择身份证有效期限-始");
            return false;
        }
        if (idCardValidTimeEnd == "") {
            alert("请选择身份证有效期限-末");
            return false;
        }
        if (idCardValidTimeBegin > idCardValidTimeEnd) {
            alert("身份证有效期限错误");
            return false;
        }
        if (idCardCopy == "") {
            alert("请选择并上传身份证人像面");
            return false;
        }
        if (idCardNational == "") {
            alert("请选择并上传身份证国徽面");
            return false;
        }
    });
</script>