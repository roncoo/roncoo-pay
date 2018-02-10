<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglib/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单管理</title>
</head>
<body>
	<style type="text/css">
ul.rightTools {
	float: right;
	display: block;
}

ul.rightTools li {
	float: left;
	display: block;
	margin-left: 5px
}
</style>
	<form id="treeForm1" onsubmit="return navTabSearch(this);" action="${baseURL }/pms/menu/list" method="post"></form>
	<div class="pageContent" style="padding: 5px">
		<div class="tabs">
			<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
						<li>
							<a href="javascript:;">
								<span>菜单管理</span>
							</a>
						</li>
					</ul>
				</div>
			</div>
			<div class="tabsContent">
				<div>
					<div class="panelBar" style="width: 262px">
						<ul class="toolBar">
							<li>
								<shiro:hasPermission name="pms:menu:add">
									<a id="addMenu" class="add" href="${baseURL}/pms/menu/addUI" target="dialog" rel="input" width="600" height="400" title="添加菜单">
										<span>添加</span>
									</a>
								</shiro:hasPermission>
							</li>
							<li>
								<shiro:hasPermission name="pms:menu:delete">
									<a id="delMenu" class="delete" href="${baseURL}/pms/menu/delete" callback="navTabAjax" target="ajaxTodo" rel="inputMenu" title="确定执行该删除操作吗？">
										<span>删除</span>
									</a>
								</shiro:hasPermission>
							</li>
							<li>
								<a id="updateMenu" class="edit" href="javascript:onscreach();">
									<span>刷新</span>
								</a>
							</li>
						</ul>
					</div>
					<div layoutH="78" style="float: left; display: block; overflow: auto; width: 260px; border: solid 1px #CCC; line-height: 21px; background: #fff">${tree}</div>
					<div layoutH="78" id="jbsxBox" class="unitBox" style="margin-left: 266px;"></div>
				</div>
			</div>
			<div class="tabsFooter">
				<div class="tabsFooterContent"></div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	function onClickMenuNode(id) {
		$("#addMenu").attr("href", "pms/menu/addUI?pid=" + id);
		$("#delMenu").attr("href", "pms/menu/delete?menuId=" + id);
	}
	function onscreach() {
		$("#treeForm1").submit();
	}

	// 删除后的回调函数，刷新树形菜单
	function navTabAjax(json) {
		//navTabAjaxDone(json);
		navTab.reload();
	}
</script>