$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/grossprofit/list',
        datatype: "json",
        colModel: [			
			{ label: '客户名称', name: 'customerName', index: 'customer_name', width: 80 }, 	
			{ label: '客户编码', name: 'customerCode', index: 'customer_code', width: 60 }, 	
			{ label: '运单号', name: 'waybillNumber', index: 'waybill_number', width: 70 }, 	
			{ label: '重量', name: 'debtorWeight', index: 'debtor_weight', width: 40 }, 	
			{ label: '目的网点', name: 'destinationDot', index: 'destination_dot', width: 80 }, 			
			{ label: '收入金额', name: 'debtorMoney', index: 'debtor_money', width: 40}, 			
			{ label: '成本金额', name: 'lenderMoney', index: 'lender_money', width:40 }, 			
			{ label: '面单金额', name: 'baseBil', index: 'base_bil', width: 40}, 			
			{ label: '毛利金额', name: 'grossProfit', index: 'gross_profit', width: 40,formatter:function(value, options, row){
				if(value<0){
					return  "<span style='color:red;'>"+value+"</span>";
				}else {
					return value;
				}
			} }, 			
			{ label: '创建时间', name: 'createDate', index: 'create_date', width: 80 }	
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        /*rownumbers: true, 
        rownumWidth: 25, */
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
      /*  prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },*/
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		expVoucher: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.expVoucher = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.expVoucher.id == null ? "sys/expvoucher/save" : "sys/expvoucher/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.expVoucher),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/expvoucher/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get(baseURL + "sys/expvoucher/info/"+id, function(r){
                vm.expVoucher = r.expVoucher;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});

function exports(){
	 var start_dates = $("#start_dates").val();
	 var end_dates = $("#end_dates").val();
	 var zero = $("#zero").val();
	 if(""==start_dates||null==start_dates){
		 alert("导出必须选择时间段")
	 };
	location.href=baseURL + "sys/grossprofit/expotslist?startDates="+start_dates+"&endDates="+end_dates+"&zero="+zero;
	
}
function query(){
	 var start_dates = $("#start_dates").val();
	 var end_dates = $("#end_dates").val();
	 var zero = $("#zero").val();
	 var page = $("#jqGrid").jqGrid('getGridParam','page');
	    $("#jqGrid").jqGrid('setGridParam',{  
	    postData:{start_dates:start_dates,end_dates:end_dates,zero:zero},
	    page:page
	     }).trigger("reloadGrid");
}
