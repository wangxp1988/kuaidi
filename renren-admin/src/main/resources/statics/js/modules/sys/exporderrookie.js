$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/exporderrookie/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '订单号', name: 'orderNumber', index: 'order_number', width: 80 }, 			
			{ label: '运单号', name: 'waybillNumber', index: 'waybill_number', width: 80 }, 			
			{ label: '创建时间', name: 'createDate', index: 'create_date', width: 80 }, 			
			{ label: '订单状态', name: 'orderStatus', index: 'order_status', width: 80 }, 			
			/*{ label: '订单来源', name: 'orderSoruce', index: 'order_soruce', width: 80 }, */			
			{ label: '网点编号', name: 'dotCode', index: 'dot_code', width: 80 }, 			
			{ label: '网点名称', name: 'dotName', index: 'dot_name', width: 80 }, 			
			{ label: '客户编号', name: 'customerCode', index: 'customer_code', width: 80 }, 			
			{ label: '客户名称', name: 'customerName', index: 'customer_name', width: 80 }, 			
			{ label: '目的网点', name: 'destinationDot', index: 'destination_dot', width: 80 }, 			
			{ label: '目的分拨', name: 'objectiveAllocation', index: 'objective_allocation', width: 80 }, 			
			{ label: '目的省份', name: 'destinationProvince', index: 'destination_province', width: 80 }, 			
			/*{ label: '目的市', name: 'destinationCity', index: 'destination_city', width: 80 }, 			
			{ label: '目的区', name: 'destinationArea', index: 'destination_area', width: 80 }, 			
			{ label: '收件地址', name: 'address', index: 'address', width: 80 }		*/	
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
		expOrderRookie: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.expOrderRookie = {};
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
			var url = vm.expOrderRookie.id == null ? "sys/exporderrookie/save" : "sys/exporderrookie/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.expOrderRookie),
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
				    url: baseURL + "sys/exporderrookie/delete",
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
			$.get(baseURL + "sys/exporderrookie/info/"+id, function(r){
                vm.expOrderRookie = r.expOrderRookie;
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
            url: baseURL + "sys/exporderrookie/import",  
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