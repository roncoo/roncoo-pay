<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单模块</title>

<script type="text/javascript">


</script>
</head> 
<body>
<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block; margin-left:5px}
</style>
<form id="treeForm" onsubmit="return navTabSearch(this);" action="tit_listTitle.action" method="post" >

<div class="pageContent" style="padding:5px; " >
	<input type="hidden"  id="roleId" value="${roleId }" />
	<div class="tabs" style="width: 320px;float:left;" >
		<div class="tabsContent">
			<div>
				<div layoutH="40" style="float:left; display:block; overflow:auto; width:310px; border:solid 1px #CCC; line-height:21px; background:#fff" id="treeDive">
				    ${tree }
				</div>
			</div>
		</div>
	</div>
	</div>
</form>
</body>