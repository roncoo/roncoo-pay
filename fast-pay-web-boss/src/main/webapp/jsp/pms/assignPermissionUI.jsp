<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglib/taglib.jsp"%>
<div class="pageContent">
<form id="form" method="post" action="${baseURL}/pms/role/assignPermission" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div class="pageFormContent" layoutH="60">
	<input type="hidden" name="navTabId" value="jsgl">
	<input type="hidden" name="callbackType" value="closeCurrent">
	<input type="hidden" name="roleId" value="${role.id }" />
	<input type="hidden" name="selectVal" id="selectVal" value="">
		
		<div class="tabs" style="width:500px;float:left;" >
			<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
						<li><a href="javascript:;"><span>分配权限</span></a></li>
					</ul>
				</div>
			</div>
			<div class="tabsContent">
				<div>
					<div id="treeDiv" layoutH="100" style="float:left; display:block;overflow:auto; width:489px; border:solid 1px #CCC; line-height:21px; background:#fff">
					    <fieldset style="width:99%">
							<legend>全选<input type="checkbox"  name="selectAll" id="selectAll" ></legend>
							<c:forEach items="${permissionList}" var="v">
								<label>
									<input type="checkbox" class="selectPer" name="selectPer" id="perId${v.id }" value="${v.id }">${v.permissionName }
								</label>
							</c:forEach>
						</fieldset>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 用户 -->
		<div class="tabs" style="width: 400px;float:left; " >
			<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
						<li><a href="javascript:;"><span>关联了此角色的操作员</span></a></li>
					</ul>
				</div>
			</div>
			<div class="tabsContent">
				<div>
					<div layoutH="100" style="float:left; display:block; overflow:auto; width:389px; border:solid 1px #CCC; line-height:21px; background:#fff" id="userDiv">
						<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="123">
							<thead>
								<tr>
									<th>序号</th>
									<th>登录名</th>
									<th>用户姓名</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="item" items="${operatorList}" varStatus="st">
									<tr target="sid_user" rel="${id}">
									    <td>${st.index+1}</td>
										<td>${item.loginName }</td>
										<td>${item.realName }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="button"  onclick="submitForm()" >保存</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
		</ul>
	</div>
	</form>
</div>
<script type="text/javascript">
//回显
$(document).ready(function() {
	var str = "${permissionIds}";
	var array = new Array();
	array = str.split(",");
	for ( var i = 0; i < array.length; i++) {
		$("#perId" + array[i]).attr("checked", "checked");
	}
	
	$("#selectAll").click(function(){
		if($("#selectAll").is(':checked')){
			$("input[name='selectPer']").attr("checked","checked"); 
		}else{
			$("input[name='selectPer']").removeAttr("checked");
		}
	}); 
});


function submitForm() {
	var str = "";
	$(":checkbox:checked").each(function() {
		if ($(this).hasClass('selectPer')){
			// 加样式判断，避免与其他复选框冲突
			str += $(this).val() + ",";
		}
	});
	if(str == null || str == ""){
		alertMsg.error("关联的权限不能为空!");
		return;
	}
	$("#selectVal").val(str);
	$("#form").submit();
}
</script>
