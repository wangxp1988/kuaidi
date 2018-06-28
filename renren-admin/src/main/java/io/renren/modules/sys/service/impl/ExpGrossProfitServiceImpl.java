package io.renren.modules.sys.service.impl;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.renren.common.annotation.DataFilter;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.ExpVoucherDao;
import io.renren.modules.sys.entity.ExpVoucherEntity;
import io.renren.modules.sys.service.ExpBaseService;
import io.renren.modules.sys.service.ExpGrossProfitService;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Service("expProssProfitService")
public class ExpGrossProfitServiceImpl extends ServiceImpl<ExpVoucherDao, ExpVoucherEntity>   implements ExpGrossProfitService {

	@Autowired
	private ExpVoucherDao expVoucherDao;
	@Autowired 
	private ExpBaseService  expBaseService;
	 @Override
	 @DataFilter(subDept = true, user = false,tableAlias="v")
	public PageUtils queryPage(Map<String, Object> params) {
		 String fillter="";
		 if(null!=params.get(Constant.SQL_FILTER)) {
			   fillter=params.get(Constant.SQL_FILTER).toString();
		 }
		 BigDecimal baseBil= expBaseService.selectBaseBill(params);
		 String startDates="";
		String endDates="";
		String zero="";
		 if(null!=params.get("start_dates")) {
			 startDates=params.get("start_dates").toString();
		 }
		 if(null!=params.get("start_dates")) {
			 startDates=params.get("start_dates").toString();
		 }
		 if(null!=params.get("zero")) {
			 zero=params.get("zero").toString();
		 }
		  
		 int count=this.expVoucherDao.selectCountMy(baseBil,startDates,endDates,(String)params.get(Constant.SQL_FILTER),zero);
		 Query query=new Query<ExpVoucherEntity>(params);
		 List<ExpVoucherEntity> list=new ArrayList<ExpVoucherEntity>();
		 ExpVoucherEntity en=this.expVoucherDao.selectPageMySum(baseBil,startDates,endDates,fillter,zero);
		 list.add(en);
		 List<ExpVoucherEntity> lists=this.expVoucherDao.selectPageMy(baseBil,(query.getCurrPage()-1)*query.getLimit(),query.getLimit(),startDates,endDates,fillter,zero);
		  list.addAll(lists);
		  return  new PageUtils(list, count, query.getLimit(), query.getCurrPage());
	}
	 @DataFilter(subDept = true, user = false)
	public List<Map<String, Object>> SelectGrossProfitSum(Map<String, Object> params) {
		 if(null!=params.get("sql_filter")) {
				params.put("sql_filter_one", params.get("sql_filter").toString().replace("dept_id", "v.dept_id"));
			}
		 BigDecimal baseBil= expBaseService.selectBaseBill(params);
		 params.put("baseBil", baseBil);
		 return this.expVoucherDao.SelectGrossProfitSum(params);
		 
	}
	 
	 @DataFilter(subDept = true, user = false)
	public List<Map<String, Object>> SelectGrossProfitSumOrderByCity(Map<String, Object> params) {
		 if(null!=params.get("sql_filter")) {
				params.put("sql_filter_one", params.get("sql_filter").toString().replace("dept_id", "v.dept_id"));
			}
		BigDecimal baseBil= expBaseService.selectBaseBill(params);
		 params.put("baseBil", baseBil);
			return this.expVoucherDao.SelectGrossProfitSumOrderByCity(params);
    } 
	 @DataFilter(subDept = true, user = false)
	 public List<Map<String, Object>> SelectGrossProfitSumOrderByWeight(Map<String, Object> params) {
		 if(null!=params.get("sql_filter")) {
				params.put("sql_filter_one", params.get("sql_filter").toString().replace("dept_id", "v.dept_id"));
			}
		 BigDecimal baseBil= expBaseService.selectBaseBill(params);
		 params.put("baseBil", baseBil);
		 return this.expVoucherDao.SelectGrossProfitSumOrderByWeight(params);
	 } 
		 
	@Override
	 @DataFilter(subDept = true, user = false,tableAlias="v")
	public void expotslist(Map<String, Object> params) {
		 String fillter="";
		 if(null!=params.get(Constant.SQL_FILTER)) {
			   fillter=params.get(Constant.SQL_FILTER).toString();
		 }
		 BigDecimal baseBil= expBaseService.selectBaseBill(params);
		 params.put("baseBil", baseBil);
		 params.put(Constant.SQL_FILTER, fillter);
		 List<ExpVoucherEntity> list=this.expVoucherDao.selectExpotsList(params);
		 
		 String filename="";
			if(null!=params.get("startDates")&&!"".equals(params.get("startDates"))) {
				filename+=params.get("startDates")+"——";
			}
	      if(null!=params.get("endDates")&&!"".equals(params.get("endDates"))) {
	    	  filename+=params.get("endDates");
				
			}
	      filename+="毛利明细.xls";
	      String[] Title= {"客户编号","客户名称","运单号","重量","目的网点","收入金额","成本金额","面单金额","毛利金额","创建时间"};
		 exportExcel((HttpServletResponse)params.get("response"), filename, Title, list);
	}
	 
	 public  String exportExcel(HttpServletResponse response,String fileName,String[] Title, List<ExpVoucherEntity> list) { 
		 SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String result="系统提示：Excel文件导出成功！";  
		 // 以下开始输出到EXCEL 
		 try {   
		  //定义输出流，以便打开保存对话框______________________begin 
		  OutputStream os = response.getOutputStream();// 取得输出流    
		  response.reset();// 清空输出流    
		  response.setHeader("Content-disposition", "attachment; filename="+ new String(fileName.getBytes("GB2312"),"ISO8859-1")); 
		// 设定输出文件头    
		  response.setContentType("application/msexcel");// 定义输出类型   
		  //定义输出流，以便打开保存对话框_______________________end 
		 
		  /** **********创建工作簿************ */ 
		  WritableWorkbook workbook = Workbook.createWorkbook(os); 
		 
		  /** **********创建工作表************ */ 
		 
		  WritableSheet sheet = workbook.createSheet("Sheet1", 0); 
		 
		  /** **********设置纵横打印（默认为纵打）、打印纸***************** */ 
		  jxl.SheetSettings sheetset = sheet.getSettings(); 
		  sheetset.setProtected(false); 
		 
		 
		  /** ************设置单元格字体************** */ 
		  WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10); 
		  WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,WritableFont.BOLD); 
		 
		  /** ************以下设置三种单元格样式，灵活备用************ */ 
		  // 用于标题居中 
		  WritableCellFormat wcf_center = new WritableCellFormat(BoldFont); 
		  wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条 
		  wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐 
		  wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐 
		  wcf_center.setWrap(false); // 文字是否换行 
		   
		  // 用于正文居左 
		  WritableCellFormat wcf_left = new WritableCellFormat(NormalFont); 
		  wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条 
		  wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐 
		  wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐 
		  wcf_left.setWrap(false); // 文字是否换行   
		  
		 
		  /** ***************以下是EXCEL开头大标题，暂时省略********************* */ 
		  //sheet.mergeCells(0, 0, colWidth, 0); 
		  //sheet.addCell(new Label(0, 0, "XX报表", wcf_center)); 
		  //String[] Title= {"客户名称","客户编号","运单号","重量","目的网点","收入金额","成本金额","面单金额","毛利金额","创建时间"};
		  /** ***************以下是EXCEL第一行列标题********************* */ 
		  for (int i = 0; i < Title.length; i++) { 
		  sheet.addCell(new Label(i, 0,Title[i],wcf_center)); 
		  }   
		  /** ***************以下是EXCEL正文数据********************* */ 
		  int i=1; 
		  for(ExpVoucherEntity entity:list){ 
			  if (null != entity.getCustomerCode() && "" != entity.getCustomerCode()) {
			    sheet.addCell(new Label(0, i,entity.getCustomerCode(), wcf_left));
			  }
			  if (null != entity.getCustomerName() && "" != entity.getCustomerName()) {
				sheet.addCell(new Label(1, i,entity.getCustomerName(), wcf_left));
			  }
			  if (null != entity.getWaybillNumber() && "" != entity.getWaybillNumber()) {
				sheet.addCell(new Label(2, i,entity.getWaybillNumber(), wcf_left));
			  }
			  if (null != entity.getDebtorWeight()) {
				sheet.addCell(new Label(3, i, entity.getDebtorWeight().toString(), wcf_left));
			  }
			  if (null != entity.getDestinationDot() && "" !=entity.getDestinationDot()) {
				sheet.addCell(new Label(4,i, entity.getDestinationDot(), wcf_left));
			  }
			  if (null != entity.getDebtorMoney()) {
				  sheet.addCell(new Label(5,i,entity.getDebtorMoney().toString(), wcf_left));
			  }
			  if (null != entity.getLenderMoney()) {
				  sheet.addCell(new Label(6,i, entity.getLenderMoney().toString(), wcf_left));
			  }
			  if (null != entity.getBaseBil()) {
				  sheet.addCell(new Label(7,i,  entity.getBaseBil().toString(), wcf_left));
			  }
			  if (null != entity.getBaseBil()) {
				  sheet.addCell(new Label(8,i,  entity.getGrossProfit().toString(), wcf_left));
			  }
			  if (null != entity.getCreateDate()) {
				  sheet.addCell(new Label(9,i,  sd.format(entity.getCreateDate()), wcf_left));
			  }
			 
		    i++; 
		  } 
		  /** **********将以上缓存中的内容写到EXCEL文件中******** */ 
		  workbook.write(); 
		  /** *********关闭文件************* */ 
		  workbook.close();   
		 
		 } catch (Exception e) { 
		  result="系统提示：Excel文件导出失败，原因："+ e.toString(); 
		  System.out.println(result);  
		  e.printStackTrace(); 
		 } 
		 return result; 
		 }


}
