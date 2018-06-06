$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/expcustomer/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '客户编码', name: 'code', index: 'code', width: 80 }, 			
			{ label: '客户名称', name: 'name', index: 'name', width: 80 }, 			
			{ label: '客户类型', name: 'type', index: 'type', width: 80 }, 			
			{ label: '价格表名称', name: 'priceName', index: 'price_name', width: 80 }, 			
			{ label: '付款客户ID', name: 'paymentId', index: 'payment_id', width: 80 }, 			
			{ label: '付款客户名称', name: 'paymentName', index: 'payment_name', width: 80 }		
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
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
		expCustomer: {},
		priceNameList:{},
		customerList:{},
		 customerType:{}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.expCustomer = {};
			vm.expCustomer.priceName="0000";
			vm.expCustomer.paymentName="0000";
			vm.expCustomer.type="0000";
			this.getPriceNameList();
			this.getCustomerList();
			this.getCustomerType();
			$("#paymentId").attr("value","");
			$("#paymentId").attr("placeholder","选择付款客户后自动添加");
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            this.getPriceNameList();
            this.getCustomerList();
            this.getCustomerType();
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.expCustomer.id == null ? "sys/expcustomer/save" : "sys/expcustomer/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.expCustomer),
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
				    url: baseURL + "sys/expcustomer/delete",
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
			$.get(baseURL + "sys/expcustomer/info/"+id, function(r){
                vm.expCustomer = r.expCustomer;
            });
		},
		getPriceNameList: function(){
            $.get(baseURL + "sys/expprice/listAllName", function(r){
                vm.priceNameList = r.list;
            });
        },
        getCustomerList: function(){
            $.get(baseURL + "sys/expcustomer/listAllCustomer", function(r){
                vm.customerList = r.list;
            });
        },
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		},
		changeCustomer:function(){
			var code=$('#selectCustomer').find("option:selected").attr("code");
			vm.expCustomer.paymentId=code;
			$("#paymentId").attr("value",code);
			$("#paymentId").attr("placeholder",code);
		},
		getCustomerType:function(){
			 $.get(baseURL + "sys/expcustomertype/listAll", function(r){
	                vm.customerType = r.list;
	            });
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
            url: baseURL + "sys/expcustomer/import",  
            data: fd,
            cache:false,
            contentType:false,
            processData:false,
            success : function(data){  
            	layer.close(index);
            	 $("#jqGrid").jqGrid('setGridParam',{}).trigger("reloadGrid");
               }     
    }); 
}

function exports(){
	location.href=baseURL + "sys/expcustomer/export"
}