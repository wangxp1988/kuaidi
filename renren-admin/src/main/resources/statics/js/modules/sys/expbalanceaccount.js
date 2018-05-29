$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/expbalanceaccount/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '运单号', name: 'waybillNumber', index: 'waybill_number', width: 80 }, 			
			{ label: '寄件人', name: 'sender', index: 'sender', width: 80 }, 			
			{ label: '网点', name: 'branch', index: 'branch', width: 80 }, 			
			{ label: '寄件时间', name: 'sendTime', index: 'send_time', width: 80 }, 			
			{ label: '寄件省份', name: 'sendProvince', index: 'send_province', width: 80 }, 			
			/*{ label: '收件人', name: 'recipient', index: 'recipient', width: 80 }, 	*/		
			{ label: '收件省份', name: 'recipientProvince', index: 'recipient_province', width: 80 }, 			
			{ label: '揽件业务员', name: 'salesman', index: 'salesman', width: 80 }, 			
			/*{ label: '月结客户名称', name: 'customerName', index: 'customer_name', width: 80 }, 			
			{ label: '月结客户手机', name: 'customerPhone', index: 'customer_phone', width: 80 }, 	*/		
			{ label: '实际重量', name: 'actualWeight', index: 'actual_weight', width: 80 }			
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
		expBalanceAccount: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.expBalanceAccount = {};
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
			var url = vm.expBalanceAccount.id == null ? "sys/expbalanceaccount/save" : "sys/expbalanceaccount/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.expBalanceAccount),
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
				    url: baseURL + "sys/expbalanceaccount/delete",
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
			$.get(baseURL + "sys/expbalanceaccount/info/"+id, function(r){
                vm.expBalanceAccount = r.expBalanceAccount;
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
            url: baseURL + "sys/expbalanceaccount/import",  
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
            	
            	 $("#jqGrid").jqGrid('setGridParam',{}).trigger("reloadGrid");
               }     
    }); 
}