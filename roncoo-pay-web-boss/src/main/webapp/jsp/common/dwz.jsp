<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../../common/taglib/taglib.jsp" %>
<link href="${baseURL }/dwz/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${baseURL }/dwz/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${baseURL }/dwz/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
<link href="${baseURL }/dwz/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>

 <!--[if IE]>
<link href="dwz/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->

<!--[if lte IE 9]>
<script src="js/speedup.js" type="text/javascript"></script>
<![endif]-->

<script src="${baseURL }/dwz/js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/jquery.cookie.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/jquery.validate.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/jquery.bgiframe.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/xheditor/xheditor-1.1.14-zh-cn.min.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/uploadify/scripts/jquery.uploadify.min.js" type="text/javascript"></script>

<script src="${baseURL }/dwz/bin/dwz.min.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>

<script src="${baseURL }/dwz/js/dwz.core.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/dwz.util.date.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/dwz.validate.method.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/dwz.barDrag.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/dwz.drag.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/dwz.tree.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/dwz.accordion.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/dwz.ui.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/dwz.theme.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/dwz.switchEnv.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/dwz.alertMsg.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/dwz.contextmenu.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/dwz.navTab.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/dwz.tab.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/dwz.resize.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/dwz.dialog.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/dwz.dialogDrag.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/dwz.sortDrag.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/dwz.cssTable.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/dwz.stable.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/dwz.taskBar.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/dwz.ajax.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/dwz.pagination.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/dwz.database.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/dwz.datepicker.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/dwz.effects.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/dwz.panel.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/dwz.checkbox.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/dwz.history.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/dwz.combox.js" type="text/javascript"></script>
<script src="${baseURL }/dwz/js/dwz.print.js" type="text/javascript"></script>


<script type="text/javascript">

$(function(){
	DWZ.init("${baseURL }/dwz/dwz.frag.xml", {
		loginUrl:"${baseURL }/login", loginTitle:"登录",	// 弹出登录对话框
		statusCode:{ok:200, error:300, timeout:301}, //【可选】
		pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
		debug:false,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"${baseURL }/dwz/themes"}); // themeBase 相对于index页面的主题base路径
			//setTimeout(function() {$("#sidebar .toggleCollapse div").trigger("click");}, 10);//index页面初始化时隐藏左边菜单.
		}
	});
});

</script>