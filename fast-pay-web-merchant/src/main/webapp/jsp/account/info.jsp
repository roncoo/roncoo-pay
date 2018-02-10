<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
  <%@include file="../../common/taglib.jsp"%>
      <div class="row">
        <div class="col-md-3">

          <!-- Profile Image -->
          <div class="box box-primary">
            <div class="box-body box-profile">
              <img class="profile-user-img img-responsive img-circle" src="${baseURL }/lte/dist/img/avatar5.png" alt="User profile picture">

              <h3 class="profile-username text-center">${rpUserInfo.userName }</h3>

              <p class="text-muted text-center">商 家</p>

              <ul class="list-group list-group-unbordered">
                <li class="list-group-item">
                  <b>账户余额</b> <a class="pull-right">¥<fmt:formatNumber value="${rpAccount.availableBalance }" pattern="0.00" /></a>
                </li>
                <li class="list-group-item">
                  <b>可结算余额</b> <a class="pull-right">¥<fmt:formatNumber value="${rpAccount.availableSettAmount }" pattern="0.00" /></a>
                </li>
                <li class="list-group-item">
                  <b>冻结金额</b> <a class="pull-right">¥<fmt:formatNumber value="${rpAccount.unbalance }" pattern="0.00" /></a>
                </li>
                <li class="list-group-item">
                  <b>今日收益</b> <a class="pull-right">¥<fmt:formatNumber value="${rpAccount.todayIncome }" pattern="0.00" /></a>
                </li>
                <li class="list-group-item">
                  <b>今日支出</b> <a class="pull-right">¥<fmt:formatNumber value="${rpAccount.todayExpend }" pattern="0.00" /></a>
                </li>
              </ul>

              <!-- <a href="#" class="btn btn-primary btn-block"><b>提 现</b></a> -->
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
        </div>
        <!-- /.col -->
        <div class="col-md-9">
          <div class="nav-tabs-custom">
            <ul class="nav nav-tabs">
              <li class="active"><a href="#config" data-toggle="tab">支付配置</a></li>
              <li><a href="#detail" data-toggle="tab">收支明细</a></li>
            </ul>
            <div class="tab-content">
              <div class="active tab-pane" id="config">
                <form class="form-horizontal">
                  <div class="form-group">
                    <label for="inputName" class="col-sm-2 control-label">手机号</label>

                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="inputEmail" value="${rpUserInfo.mobile }" disabled="disabled"/>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputEmail" class="col-sm-2 control-label">支付方式</label>
					
                    <div class="col-sm-10">
                      <c:forEach var="item" items="${rpPayWayList}" varStatus="s">
						<div class="checkbox">
		                    <label>
		                      <input type="checkbox" disabled checked>
		                      ${item.payWayName }-${item.payTypeName }
		                    </label>
		                  </div>
					</c:forEach>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputName" class="col-sm-2 control-label">支付key</label>

                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="inputName" value="${rpUserPayConfig.payKey }" disabled="disabled">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputExperience" class="col-sm-2 control-label">支付密钥</label>

                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="inputName" value="${rpUserPayConfig.paySecret }" disabled="disabled">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputSkills" class="col-sm-2 control-label">收款方式</label>

                    <div class="col-sm-10">
                      <div class="checkbox">
		                    <label>
		                      <input type="checkbox" disabled checked>
		                      ${rpUserPayConfig.fundIntoTypeDesc }
		                    </label>
		                  </div>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputExperience" class="col-sm-2 control-label">风险预存期</label>

                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="inputName" value="T+${rpUserPayConfig.riskDay }" disabled="disabled">
                    </div>
                  </div>
                  
                </form>
              </div>
              <!-- /.tab-pane -->
              <div class="tab-pane" id="detail">
               	<table id="pageTable" class="table table-bordered table-hover">
	                <thead>
		                <tr>
		                  <th>业务类型</th>
		                  <th>收入/支出</th>
		                  <th>余额</th>
		                  <th>时间</th>
		                </tr>
	                </thead>
                <tbody>
                </tbody>
              </table>
              </div>
              <!-- /.tab-pane -->
            </div>
            <!-- /.tab-content -->
          </div>
          <!-- /.nav-tabs-custom -->
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->

<!-- DataTables -->
<script src="${baseURL}/lte/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="${baseURL}/lte/plugins/datatables/dataTables.bootstrap.min.js"></script>
<!-- page script -->
<script>
function retrieveData( sSource, aoData, fnCallback ) {      
    
    $.ajax( {     
        "type": "POST",      
        "contentType": "application/json",     
        "url": sSource,      
        "dataType": "json",     
        "data": JSON.stringify(aoData), //以json格式传递     
        "success": function(resp) {     
            fnCallback(resp); //服务器端返回的对象的returnObject部分是要求的格式    
        }     
    });      
}    

var oTable = null;     

$(function() {     
    search();
} );     
    
//“检索”按钮的处理函数     
function search() {     
    if (oTable == null) { //仅第一次检索时初始化Datatable     
        $("#pageTable").show();     
        oTable = $('#pageTable').dataTable( {     
            "bAutoWidth": false,                    //不自动计算列宽度     
            "aoColumns": [                          //设定各列宽度     
                                                    {"mData" : "trxTypeDesc"},
                                                    {"mData" : "amountDesc"},
                                                    {"mData" : "balance"},
                                                    {"mData" : "createTimeDesc"}
                                                ],        
            "bProcessing": true,                    //加载数据时显示正在加载信息     
            "bServerSide": true,                    //指定从服务器端获取数据     
            "bFilter": false,                       //不使用过滤功能     
            "bLengthChange": false,                 //用户不可改变每页显示数量     
            "iDisplayLength": 10,                    //每页显示10条数据     
            "sAjaxSource": "${baseURL }/merchant/account/ajaxAccountInfo",//获取数据的url     
            "fnServerData": retrieveData,           //获取数据的处理函数     
            "sPaginationType": "full",      //翻页界面类型     
            "searching": false,
            "ordering": false,
            "oLanguage": {                          //汉化     
                "sZeroRecords": "没有检索到数据",     
                "sInfo": "总共有 _TOTAL_ 条记录",     
                "sInfoEmtpy": "没有数据",     
                "sProcessing": "正在加载数据...",     
                "oPaginate": {     
                    "sFirst": "首页",     
                    "sPrevious": "前一页",     
                    "sNext": "后一页",     
                    "sLast": "尾页"    
                }     
            }     
        });     
    }     
    
    //刷新Datatable，会自动激发retrieveData     
    oTable.fnDraw();     
}    
</script>

