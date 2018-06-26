$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/expprice/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '价格名称', name: 'priceName', index: 'price_name', width: 80 }, 			
			{ label: '省份名称', name: 'provinceName', index: 'province_name', width: 80 }, 			
			{ label: '重量', name: 'weight', index: 'weight', width: 80 }, 			
			{ label: '快递费用', name: 'money', index: 'money', width: 80 }			
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
	  
    created: function () {
	 $.get(baseURL + "sys/sysarea/listAll", function(r){
         vm.provinceList = r.list;
     });
	},
	data:{
		q:{
			priceName: null,
			provinceName: "",
			weight: "",
		    provinceList:{}
        },
		showList: true,
		title: null,
		expPrice: {},
		provinceList:{}
	},
 	methods: {
		 created: function (){
			 this.getProvinceList()
		 },
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.provinceList={};
			vm.expPrice = {};
			vm.expPrice.provinceName="0000";
			vm.expPrice.weight="0000";
			this.getProvinceList();
			
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            this.getProvinceList();
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.expPrice.id == null ? "sys/expprice/save" : "sys/expprice/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.expPrice),
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
		delAll:function(){
			confirm('确定要清空您的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/expprice/deleteAll",
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
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/expprice/delete",
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
			$.get(baseURL + "sys/expprice/info/"+id, function(r){
                vm.expPrice = r.expPrice;
            });
		},
		getProvinceList: function(){
            $.get(baseURL + "sys/sysarea/listAll", function(r){
                vm.provinceList = r.list;
            });
        },
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
			   postData:{'priceName': vm.q.priceName,'provinceName':vm.q.provinceName,'weight': vm.q.weight},
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
            url: baseURL + "sys/expprice/import",  
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
	location.href=baseURL + "sys/expprice/export"
}