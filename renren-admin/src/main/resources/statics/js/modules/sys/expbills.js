$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/receivables/billlist',
        datatype: "json",
        colModel: [			
        	{ label: '凭证摘要', name: 'voucherRemark', width: 80 }, 	
			{ label: '客户名称', name: 'customerName', width: 80 }, 	
			{ label: '客户编码', name: 'customerCode', width: 60 },	
			{ label: '借方金额', name: 'debtorSum', width: 40 }, 	
			{ label: '贷方金额', name: 'lenderSum', width: 80 }			
			 		
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

/*function exports(){
	var index = layer.load(1, {
	   	  shade: [0.2,'#fff'] //0.1透明度的白色背景
	   	});
	var dates=$("#dates").val();
	if(null==dates||dates==""){
		 layer.close(index);
		alert("请选择日期");
		return;
	}
	location.href=baseURL + "sys/expvoucher/export?dates="+dates;
	
}*/
function query(){
	 var start_dates = $("#start_dates").val();
	 var end_dates = $("#end_dates").val();
	 var type =$("#type").val();
	 var type =$("#type").val();
	 var expcustomer_code =$("#expcustomer_code").val();
	 var page = $("#jqGrid").jqGrid('getGridParam','page');
	    $("#jqGrid").jqGrid('setGridParam',{  
	    postData:{start_dates:start_dates,end_dates:end_dates,type:type,expcustomer_code:expcustomer_code},
	    page:page
	     }).trigger("reloadGrid");
}

function exports(file){
	 var start_dates = $("#start_dates").val();
	 var end_dates = $("#end_dates").val();
	 var type =$("#type").val();
	 var exportType =$("#exportType").val();
	if(null==start_dates||start_dates==""){
		alert("请选择开始日期");
		return;
	}
	if(null==end_dates||end_dates==""){
		alert("请选择结束日期");
		return;
	}
	if(null==type||type==""){
		alert("请选择客户类型");
		return;
	}
	var url="sys/receivables/export";
	if(file!=null&&file!=""){
		location.href=baseURL + "sys/receivables/downzip?fileName="+file;
		return;
	}
	var index = layer.load(2, {
	   	  shade: [0.2,'#fff'] //0.1透明度的白色背景
	   	});
	$.ajax({
	    type: "POST",
	    url:  baseURL + url,
	    data: {'start_dates':start_dates,'end_dates':end_dates,'type':type,"exportType":exportType},
	    success: function(r){
	    if(r.code === 0){
	        file=r.fileName;
	        layer.close(index);
	        exports(file);
	    }else{
	    	 layer.close(index);
	        alert(r.msg);
	    }
	}
	});
	
	//location.href=baseURL + "sys/receivables/export?start_dates="+start_dates+"&end_dates="+end_dates+"&type="+type;
	
	
}
function exportOne(file){
	 var start_dates = $("#start_dates").val();
	 var end_dates = $("#end_dates").val();
	 var expcustomer_code =$("#expcustomer_code").val();
	 var exportType =$("#exportType").val();
	if(null==start_dates||start_dates==""){
		alert("请选择开始日期");
		return;
	}
	if(null==end_dates||end_dates==""){
		alert("请选择结束日期");
		return;
	}
	if(null==expcustomer_code||expcustomer_code==""){
		alert("请输入客户编码");
		return;
	}
	location.href=baseURL + "sys/receivables/exportOne?start_dates="+start_dates+"&end_dates="+end_dates+"&customerCode="+expcustomer_code+"&exportType="+exportType;
	}



