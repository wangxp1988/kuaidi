$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/expmoneyinout/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '运单号', name: 'waybillNumber', index: 'waybill_number', width: 80 }, 			
			{ label: '时间', name: 'createDate', index: 'create_date', width: 80 }, 			
			{ label: '列名', name: 'columnName', index: 'column_name', width: 80 }, 			
			{ label: '金额', name: 'money', index: 'money', width: 80 }			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
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
		expMoneyInOut: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.expMoneyInOut = {};
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
			var url = vm.expMoneyInOut.id == null ? "sys/expmoneyinout/save" : "sys/expmoneyinout/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.expMoneyInOut),
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
				    url: baseURL + "sys/expmoneyinout/delete",
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
			$.get(baseURL + "sys/expmoneyinout/info/"+id, function(r){
                vm.expMoneyInOut = r.expMoneyInOut;
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
            url: baseURL + "sys/expmoneyinout/import",  
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
               }     
    }); 
}