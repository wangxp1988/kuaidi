$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/expgeneralinout/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true,hidden:true },
			{ label: '客户编码', name: 'customerId', index: 'customer_id', width: 80 }, 			
			{ label: '运单号', name: 'waybillNumber', index: 'waybill_number', width: 80 }, 			
			{ label: '客户', name: 'consumer', index: 'consumer', width: 80 }, 			
			{ label: '款项说明', name: 'moneyDetail', index: 'money_detail', width: 80 }, 			
			{ label: '收入金额', name: 'moneyIn', index: 'money_in', width: 80 }, 			
			{ label: '支出金额', name: 'moneyOut', index: 'money_out', width: 80 }, 			
			{ label: '账户', name: 'account', index: 'account', width: 80 }, 			
			{ label: '备注', name: 'remarks', index: 'remarks', width: 80 }, 			
			{ label: '记账日期', name: 'createTime', index: 'create_time', width: 80 } 			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 100, 
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
            rows:"limit", 
            order: "order"
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
		expGeneralInOut: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.expGeneralInOut = {};
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
			var url = vm.expGeneralInOut.id == null ? "sys/expgeneralinout/save" : "sys/expgeneralinout/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.expGeneralInOut),
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
				    url: baseURL + "sys/expgeneralinout/delete",
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
			$.get(baseURL + "sys/expgeneralinout/info/"+id, function(r){
                vm.expGeneralInOut = r.expGeneralInOut;
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

function imports(){
	var fd=new FormData();
	fd.append("file",$("#myfile").get(0).files[0]);
	 var index = layer.load(1, {
	   	  shade: [0.8,'#fff'] //0.1透明度的白色背景
	   	});
        $.ajax({  
            type: 'POST',  
            url: baseURL + "sys/expgeneralinout/import",  
            data: fd,
            cache:false,
            contentType:false,
            processData:false,
            success : function(data){  
            	layer.close(index);
            	if(data.code==0){
            		alert("数据导入成功");
            	}else{
            		alert(data.msg)
            	}
            	$("#jqGrid").trigger("reloadGrid");
            	 //$("#result").html("<span style='color:red;'>"+data.msg+"<span>") 
               }     
    }); 
}