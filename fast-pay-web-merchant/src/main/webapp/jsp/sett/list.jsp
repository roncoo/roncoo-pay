<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
  <%@include file="../../common/taglib.jsp"%>
 <style>
 #pageTableSett .more{
 	max-width:200px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
 }
 </style>
      <div class="row">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
              <h3 class="box-title">
              </h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <table id="pageTableSett" class="table table-bordered table-hover">
              
                <thead>
                <tr>
                  <th>结算金额</th>
                  <th>结算手续费</th>
                  <th>银行名称</th>
				  <th>开户名</th>
                  <th>开户账户</th>
                  <th>结算状态</th>
                  <th>创建时间</th>
                  <th >备注</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
               
              </table>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
        </div>
        <!-- /.col -->
      </div>
     <!-- DataTables -->
<script src="${baseURL }/lte/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="${baseURL }/lte/plugins/datatables/dataTables.bootstrap.min.js"></script>
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

var oTableSett = null;     

$(function() {     
    searchSett();
} );     
    
//“检索”按钮的处理函数     
function searchSett() {     
    if (oTableSett == null) { //仅第一次检索时初始化Datatable     
        $("#pageTableSett").show();     
        oTableSett = $('#pageTableSett').dataTable( {     
            "bAutoWidth": false,                    //不自动计算列宽度     
            "aoColumns": [                          //设定各列宽度     
                            {"mData" : "settAmount"},
                            {"mData" : "settFee"},
                            {"mData" : "bankName"},
                            {"mData" : "bankAccountName"},
                            {"mData" : "bankAccountNo"},
                            {"mData" : "settStatusDesc"},
                            {"mData" : "createTimeDesc"},
                            {"mData" : "remitRemark"}
                        ],
             "columnDefs": [ {
                 "targets": -1,
                 "data": null,
                 "render": function(data){
                	 console.log(data)
     				return '<div class="more" title="'+data+'">'+data+'</div>'
     			}
             } ],
            "bProcessing": true,                    //加载数据时显示正在加载信息     
            "bServerSide": true,                    //指定从服务器端获取数据     
            "bFilter": false,                       //不使用过滤功能     
            "bLengthChange": false,                 //用户不可改变每页显示数量     
            "iDisplayLength": 10,                    //每页显示10条数据     
            "sAjaxSource": "${baseURL }/merchant/sett/ajaxSettList",//获取数据的url     
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
    oTableSett.fnDraw();     
}    


</script>