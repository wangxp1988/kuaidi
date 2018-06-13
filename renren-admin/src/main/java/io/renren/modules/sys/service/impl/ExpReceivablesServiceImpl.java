package io.renren.modules.sys.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.renren.common.annotation.DataFilter;
import io.renren.common.utils.ExportExcelBatch;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.ExpCustomerDao;
import io.renren.modules.sys.dao.ExpVoucherDao;
import io.renren.modules.sys.entity.ExpCustomerEntity;
import io.renren.modules.sys.entity.ExpVoucherEntity;
import io.renren.modules.sys.service.ExpCustomerService;
import io.renren.modules.sys.service.ExpReceivablesService;
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

@Service("expReceivablesService")
public class ExpReceivablesServiceImpl extends ServiceImpl<ExpVoucherDao, ExpVoucherEntity> implements ExpReceivablesService  {

	@Autowired
	private ExpVoucherDao expVoucherDao;
	@Autowired
	private ExpCustomerService expCustomerService;
	@Override
	@DataFilter(subDept = true, user = false,tableAlias="c")
	public PageUtils queryPage(Map<String, Object> params) {
			 int count=this.expVoucherDao.selectReceivablesCount(params);
			 Query query=new Query<Map<String, Object>>(params);
			 params.put("currPage", (query.getCurrPage()-1)*query.getLimit());
			 params.put("limit", query.getLimit());
			 List<Map<String, Object>> list=this.expVoucherDao.selectReceivables(params);
		     return  new PageUtils(list, count, query.getLimit(), query.getCurrPage());
	}
	@Override
	/**
	 * 账单分页
	 */
	@DataFilter(subDept = true, user = false,tableAlias="v")
	public PageUtils queryReceivablesPage(Map<String, Object> params) {
		 int count=this.expVoucherDao.selectReceivablesByCodesCount(params);
		 Query query=new Query<Map<String, Object>>(params);
		 params.put("currPage", (query.getCurrPage()-1)*query.getLimit());
		 params.put("limit", query.getLimit());
		 List<Map<String, Object>> list=this.expVoucherDao.selectReceivablesByCodes(params);
	     return  new PageUtils(list, count, query.getLimit(), query.getCurrPage());
	}
	@Override
	/**
	 * 批量导出
	 */
	@DataFilter(subDept = true, user = false,tableAlias="v")
	public String receivablesExport(Map<String, Object> params) {
		 List<ExpCustomerEntity> customerCode= expCustomerService.selectCustomerByType(params);
		 //String[] Title={"日期","凭证摘要","初期余额","借方金额","贷方金额","期末余额","凭证号码"};
		 String[] Title={"日期","凭证摘要","借方金额","贷方金额","凭证号码"};
		 String diskDirPath=params.get("diskDirPath").toString();
		 File zip = new File(diskDirPath+ File.separator +"("+params.get("start_dates")+"至"+params.get("end_dates")+")运费表"+new Date().getTime()+ ".zip");// 压缩文件  
		 List<String> listFile=new ArrayList<String>();
		 customerCode.forEach(item->{
			 params.put("customerCode", item.getCode());
			 List<Map<String, Object>> list=expVoucherDao.selectReceivablesByCode(params);
			 /*BigDecimal initialBalance=expVoucherDao.selectInitialBalance(params);
			 if(null==initialBalance) {
				 initialBalance=new BigDecimal(0);
			 }
			 params.put("initialBalance", initialBalance);
			 BigDecimal endingBalance=expVoucherDao.selectEndingBalance(params);*/
			 String fileName=item.getName()+"应付运费-("+params.get("start_dates")+"至"+params.get("end_dates")+")运费表"+new Date().getTime(); 
			 if(null!=list&&list.size()>0) {
				 String file=ExportExcelBatch.exportExcel(fileName, Title, list, diskDirPath,item.getName(),params);
				 listFile.add(file); 
			 }
			
		 });
		
		 File srcfile[] = new File[listFile.size()];  
	        for (int i = 0, n1 = listFile.size(); i < n1; i++) {  
	            srcfile[i] = new File(listFile.get(i));  
	        } 
	    ExportExcelBatch.ZipFiles(srcfile, zip); 
	    for(File file :srcfile) {
	    	file.delete();
	    }
	    return zip.getName();
	   
	}
	@Override
	@DataFilter(subDept = true, user = false,tableAlias="c")
	public void receivablesListExport(Map<String, Object> params) {
		List<Map<String, Object>> list=this.expVoucherDao.selectReceivables(params);
		String[] Title={"客户编码","客户名称","客户类型","借方金额","贷方金额"};
		String filename="";
		if(null!=params.get("start_dates")&&!"".equals(params.get("start_dates"))) {
			filename+=params.get("start_dates")+"——";
		}
      if(null!=params.get("end_dates")&&!"".equals(params.get("end_dates"))) {
    	  filename+=params.get("end_dates");
			
		}
      if(null!=params.get("type")&&!"".equals(params.get("type"))) {
    	  filename+=params.get("type");
       }
		exportExcel((HttpServletResponse)params.get("response"),filename+"应收账单统计表.xls",Title,list);
	}
	
	
	public String exportExcel(HttpServletResponse response,String fileName,String[] Title, List<Map<String, Object>> list) { 
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
		  /** ***************以下是EXCEL第一行列标题********************* */ 
		  for (int i = 0; i < Title.length; i++) { 
		  sheet.addCell(new Label(i, 0,Title[i],wcf_center)); 
		  }   
		  /** ***************以下是EXCEL正文数据********************* */ 
		  int i=1; 
		  for(Map<String,Object> map:list){ 
			  if (null != map.get("customerCode") && "" != map.get("customerCode")) {
			    sheet.addCell(new Label(0, i,map.get("customerCode").toString(), wcf_left));
			  }
			  if (null != map.get("customerName") && "" != map.get("customerName")) {
				sheet.addCell(new Label(1, i,map.get("customerName").toString(), wcf_left));
			  }
			  if (null != map.get("customerType") && "" != map.get("customerType")) {
				sheet.addCell(new Label(2, i,map.get("customerType").toString(), wcf_left));
			  }
			  if (null != map.get("debtorSum") && "" != map.get("debtorSum")) {
				sheet.addCell(new Label(3, i, map.get("debtorSum").toString(), wcf_left));
			  }
			  if (null != map.get("lenderSum") && "" != map.get("lenderSum")) {
				sheet.addCell(new Label(4,i, map.get("lenderSum").toString(), wcf_left));
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
