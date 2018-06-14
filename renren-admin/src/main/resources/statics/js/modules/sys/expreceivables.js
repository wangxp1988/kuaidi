$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/receivables/list',
        datatype: "json",
        colModel: [			
			{ label: '客户名称', name: 'customerName', width: 80 }, 	
			{ label: '客户编码', name: 'customerCode', width: 60 }, 	
			{ label: '客户类型', name: 'customerType', width: 70 }, 	
			{ label: '借方金额', name: 'debtorSum', width: 40 }, 	
			{ label: '贷方金额', name: 'lenderSum', width: 80 }, 			
			{ label: '初期余额', name: 'initialBalance', width: 40}, 			
			{ label: '期末余额', name: 'endingBalance', width:40 }			
        ],
		viewrecords: true,
        height: 600,
        rowNum: 15,
		rowList : [15,30,50],
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
       prmNames : {
            page:"page", 
            rows:"limit" 
        }, 
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
	 var type =$("#type").val();
	location.href=baseURL + "sys/receivables/expotslist??start_dates="+start_dates+"&end_dates="+end_dates+"&type="+type;
	
}
function query(){
	 var start_dates = $("#start_dates").val();
	 var end_dates = $("#end_dates").val();
	 var type = $("#type").val();
	 var page = $("#jqGrid").jqGrid('getGridParam','page');
	    $("#jqGrid").jqGrid('setGridParam',{  
	    postData:{start_dates:start_dates,end_dates:end_dates,type:type},
	    page:page
	     }).trigger("reloadGrid");
}
