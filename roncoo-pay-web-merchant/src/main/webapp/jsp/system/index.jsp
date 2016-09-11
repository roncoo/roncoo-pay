<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <%@include file="../../common/taglib.jsp"%>
  <%@include file="../../common/lte.jsp"%>
  <title>龙果支付-商户后台</title>
    <style>
    .modal {
display:none;;

    }

    .example-modal .modal {
      background: transparent !important;
    }
    
    #alertDiv{
    	
	    position: fixed;
	    z-index: 99999;
	    width: 330px;
	    top: 0;
	    left: 50%;
	    margin-left: -165px;
    }
    .pagination {
    margin: 0px; 
}
.dataTables_paginate {
    text-align: right;
}
.dataTables_info{
    margin-top: 5px;
}
  </style>
</head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />

<body class="hold-transition ajax-template skin-blue fixed">
<div class="wrapper">
<div id="alertDiv" style="display: none;">
<div class="alert alert-success alert-dismissible">
                <button type="button" class="close" onclick="closeAlert()">&times;</button>
                <span id="alertMsg"></span>
              </div>
</div>
  <header class="main-header">
	
    <!-- Logo -->
    <a href="${baseURL }/index" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini"><b>龙果</b></span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg"><img src="${baseURL }/lte/images/logo.png" /></span>
    </a>

    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a>
      <!-- Navbar Right Menu -->
      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
         
          <!-- User Account: style can be found in dropdown.less -->
          <li class="dropdown user user-menu">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <img src="${baseURL }/lte/dist/img/avatar5.png" class="user-image" alt="User Image">
              <span class="hidden-xs">${rpUserInfo.userName }</span>
            </a>
            <ul class="dropdown-menu">
              <!-- User image -->
              <li class="user-header">
                <img src="${baseURL }/lte/dist/img/avatar5.png" class="img-circle" alt="User Image">

                <p>
                  ${rpUserInfo.userName } - 商 家
                  <small><fmt:formatDate value="${rpUserInfo.createTime}" pattern="yyyy-MM-dd" /></small>
                </p>
              </li>
              <!-- Menu Footer-->
              <li class="user-footer">
                <div class="pull-left">
                  <a href="#" onclick="editPassword();" class="btn btn-default btn-flat">修改密码</a>
                </div>
                <div class="pull-right">
                  <a href="${baseURL }/logout" class="btn btn-default btn-flat">退 出</a>
                </div>
              </li>
            </ul>
          </li>
        </ul>
      </div>

    </nav>
  </header>
  <!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      <!-- Sidebar user panel -->
      <div class="user-panel">
        <div class="pull-left image">
          <img src="${baseURL }/lte/dist/img/avatar5.png" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
          <p>${rpUserInfo.userName }</p>
          <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
        </div>
      </div>
      <!-- /.search form -->
      <!-- sidebar menu: : style can be found in sidebar.less -->
      <ul class="sidebar-menu">
        <li class="header">系统菜单</li>
      	<li class="treeview">
          <a href="${baseURL }/merchant/account/getAccountInfo">
            <i class="fa fa-dashboard"></i> <span>账户信息</span>
            <span class="pull-right-container">
            </span>
          </a>
        </li>
        <li class="treeview">
          <a href="${baseURL }/merchant/trade/getPaymentList">
            <i class="fa fa-edit"></i>
            <span>交易订单信息</span>
            <span class="pull-right-container">
              
            </span>
          </a>
        </li>
        
      </ul>
    </section>
    <!-- /.sidebar -->
  </aside>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
        <div class="modal" id="password_modal">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="editPassword()">
                  <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">修改密码</h4>
              </div>
              <form class="form-horizontal">
              <div class="modal-body">
                
                  <div class="form-group">
                    <label for="inputName" class="col-sm-2 control-label">原 密 码</label>

                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="oldPassword" name="oldPassword" value=""/>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputName" class="col-sm-2 control-label">新 密 码</label>

                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="newPassword" name="newPassword" value=""/>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputName" class="col-sm-2 control-label">确认密码</label>

                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="newPassword2" name="newPassword2" value=""/>
                    </div>
                  </div>
                
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal" onclick="editPassword()">关 闭</button>
                <button type="button" class="btn btn-primary" onclick="savePassword()">保 存</button>
              </div>
              </form>
            </div>
            <!-- /.modal-content -->
          </div>
          <!-- /.modal-dialog -->
        </div>
        <!-- /.modal -->


    <!-- Main content -->
    <section class="content">
      <div class="row">
        <div class="col-md-12">
          <!-- Bar chart -->
          <div class="box box-primary">
            <div class="box-header with-border">
              <i class="fa fa-bar-chart-o"></i>

              <h3 class="box-title">月交易汇总</h3>

              <div class="box-tools pull-right">
                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                </button>
                <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
              </div>
            </div>
            <div class="box-body">
              <div id="bar-chart" style="height: 300px;"></div>
            </div>
            <!-- /.box-body-->
          </div>
          <!-- /.box -->
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

  <footer class="main-footer">
    <div class="pull-right hidden-xs">
    </div>
    <strong>Copyright &copy; 2015-2016 <a href="http://roncoo.com">广州市领课网络科技有限公司</a></strong> 版权所有
  </footer>


  <!-- /.control-sidebar -->
  <!-- Add the sidebar's background. This div must be placed
       immediately after the control sidebar -->
  <div class="control-sidebar-bg"></div>

</div>
<!-- FLOT CHARTS -->
<script src="${baseURL }/lte/plugins/flot/jquery.flot.min.js"></script>
<!-- FLOT RESIZE PLUGIN - allows the chart to redraw when the window is resized -->
<script src="${baseURL }/lte/plugins/flot/jquery.flot.resize.min.js"></script>
<!-- FLOT PIE PLUGIN - also used to draw donut charts -->
<script src="${baseURL }/lte/plugins/flot/jquery.flot.pie.min.js"></script>
<!-- FLOT CATEGORIES PLUGIN - Used to draw bar charts -->
<script src="${baseURL }/lte/plugins/flot/jquery.flot.categories.min.js"></script>
<script type="text/javascript">
	function editPassword(){
		if($('#password_modal').css('display') == 'block'){
			$("#password_modal").hide();
		}else{
			$("#password_modal").show();
		}
	}
	
	function closeAlert(){
		$("#alertDiv").hide();
	}
	
	function savePassword(){
		var oldPassword = $("#oldPassword").val();
		var newPassword = $("#newPassword").val();
		var newPassword2 = $("#newPassword2").val();
		if(oldPassword == "" || newPassword == ""){
			$("#alertDiv").show();
        	$("#alertMsg").html("请输入密码");
		}else if(newPassword != newPassword2){
			$("#alertDiv").show();
        	$("#alertMsg").html("请确认密码一致");
		}else{
			$.ajax({  
	            type: "POST",
	            data: {oldPassword:oldPassword, newPassword:newPassword},
	            dataType:'json',
	            url: "${baseURL }/merchant/account/savePassword",
	            //请求成功完成后要执行的方法  
	            success: function(result){
	            	$("#password_modal").hide();
	            	$("#alertDiv").show();
	            	$("#alertMsg").html(result.msg);
	            },  
	            error : function() {
	            	$("#password_modal").hide();
	            	$("#alertDiv").show();
	            	$("#alertMsg").html("系统异常");
	            }   
	        });
		}
	}
	
	$(function () {
		
		$.ajax({  
            type: "POST",
            dataType:'json',
            url: "${baseURL }/merchant/trade/ajaxPaymentReport",
            //请求成功完成后要执行的方法  
            success: function(result){
            	var dataArr = [];  
            	for (var i =0;i<result.length;i++){  
            		dataArr.push([result[i].createTime, result[i].recordCount]);
            	}
            	/*
        	     * BAR CHART
        	     * ---------
        	     */
        	    var bar_data = {
        	      data: dataArr,
        	      color: "#3c8dbc"
        	    };
        	    $.plot("#bar-chart", [bar_data], {
        	      grid: {
        	        borderWidth: 1,
        	        borderColor: "#f3f3f3",
        	        tickColor: "#f3f3f3"
        	      },
        	      series: {
        	        bars: {
        	          show: true,
        	          barWidth: 0.5,
        	          align: "center"
        	        }
        	      },
        	      xaxis: {
        	        mode: "categories",
        	        tickLength: 0
        	      }
        	    });
        	    /* END BAR CHART */
            },  
            error : function() {
            	$("#password_modal").hide();
            	$("#alertDiv").show();
            	$("#alertMsg").html("获取报表异常");
            }   
        });

	  });

	  /*
	   * Custom Label formatter
	   * ----------------------
	   */
	  function labelFormatter(label, series) {
	    return '<div style="font-size:13px; text-align:center; padding:2px; color: #fff; font-weight: 600;">'
	        + label
	        + "<br>"
	        + Math.round(series.percent) + "%</div>";
	  }
</script>
</body>
</html>