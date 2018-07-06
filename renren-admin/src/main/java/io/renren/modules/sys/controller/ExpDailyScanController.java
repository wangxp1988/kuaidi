package io.renren.modules.sys.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.POIXMLException;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.renren.modules.sys.entity.ExpDailyScanEntity;
import io.renren.modules.sys.service.ExpDailyScanService;
import io.renren.modules.sys.service.ExpPriceService;
import io.renren.modules.sys.shiro.ShiroUtils;
import jxl.Sheet;
import jxl.Workbook;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.utils.UploadAndExcelUtil;



/**
 * 每日扫描
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-25 09:29:17
 */
@RestController
@RequestMapping("sys/expdailyscan")
public class ExpDailyScanController {
	@Value("${filepath.excleSavePath}")
	private  String diskDirPath;
    @Autowired
    private ExpDailyScanService expDailyScanService;
    @Autowired
    private ExpPriceService expPriceService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:expdailyscan:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = expDailyScanService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:expdailyscan:info")
    public R info(@PathVariable("id") Long id){
        ExpDailyScanEntity expDailyScan = expDailyScanService.selectById(id);

        return R.ok().put("expDailyScan", expDailyScan);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:expdailyscan:save")
    public R save(@RequestBody ExpDailyScanEntity expDailyScan){
        expDailyScanService.insert(expDailyScan);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:expdailyscan:update")
    public R update(@RequestBody ExpDailyScanEntity expDailyScan){
        ValidatorUtils.validateEntity(expDailyScan);
        expDailyScanService.updateAllColumnById(expDailyScan);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:expdailyscan:delete")
    public R delete(@RequestBody Long[] ids){
    	expDailyScanService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }
    
    @RequestMapping("import")
   	public R Import(@RequestParam("file") MultipartFile file) throws Exception {
       	String filePath = UploadAndExcelUtil.saveFile(file,diskDirPath);
       	List  list=getAllByExcel(filePath);
       	if(list.get(0).equals(Constant.EXIST)) {
    		return R.error("文件已经导入，不能重复导入");
    	}else if(list.get(0).equals(Constant.FILE_ERROR)) {
    		return R.error("文件错误");
    	}
       	if (null != list) {
       		long startTime=System.currentTimeMillis(); 
   			int g=list.size()/100;
   			int j=0;
   			j=g;
   			if(list.size()%100>0){
   				j++;
   			}
   			System.out.println("j=="+j);
   			if(j==g){
   				for (int i=0;i<g;i++) {
   					List<ExpDailyScanEntity> tempList=new ArrayList<ExpDailyScanEntity>();
   					tempList.addAll(list.subList(i*100, (i+1)*100));
   					expDailyScanService.saveList(tempList);
   				}
   			}else if(j>g){
   				for (int i=0;i<j;i++) {
   					List<ExpDailyScanEntity> tempList=new ArrayList<ExpDailyScanEntity>();
   					if(i<j-1){
   						tempList.addAll(list.subList(i*100, (i+1)*100));
   					}else if(i==j-1){
   						tempList.addAll(list.subList(i*100, (i*100+list.size()%100)));
   					}
   					expDailyScanService.saveList(tempList);
   				}
   			}
   			long endTime=System.currentTimeMillis(); //获取结束时间
   			System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
       	}
       	
       	return R.ok();
       }
       
    
    
    public  List getAllByExcel(String file) throws Exception{
    	
    	//BigDecimal bigWeight= expPriceService.selectBigWeight();
    	List list=new ArrayList(); 
        String fileType=file.substring(file.lastIndexOf(".")+1); 
        try { 
          if("xls".equalsIgnoreCase(fileType)){ 
            list= getAllByExcel2003(file); 
          }else{ 
            list= getAllByExcel2007(file); 
          } 
        } catch(OfficeXmlFileException e){//通过手动修改文件名 引起的异常 比如 3.xlsx 重命名 3.xls 其实际文件类型为xlsx 
          list=getAllByExcel2007(file); 
        } catch(POIXMLException e){//通过手动修改文件名 引起的异常 比如 3.xls 重命名 3.xlsx 其实际文件类型为xls 
          list=getAllByExcel2003(file); 
        } 
        return list; 
    }
    
    public  List getAllByExcel2007(String file) throws Exception {
    	Long deptId = ShiroUtils.getUserEntity().getDeptId();//获取登录用的部门ID
    	List list = new ArrayList();
		Map<String, Object> params=new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
		 FileInputStream in=new FileInputStream(file); 
		    XSSFWorkbook wb=new XSSFWorkbook(in); 
		    XSSFSheet sheet = wb.getSheetAt(0); 
		    int rows = sheet.getPhysicalNumberOfRows(); 
		    XSSFRow row=sheet.getRow(0); 
		    int cells=row.getLastCellNum(); 
		    Object[] csr=null; 
		    for(int i=1;i<rows;i++){ 
		      row=sheet.getRow(i); 
		      csr=new String[cells]; 
		      int j=0;
					String waybillNumber = getValue(row,j++);
   					// 扫描网点
   					String branch =getValue(row,j++);
   					// 扫描人
   					String person =getValue(row,j++);
   					// 扫描时间
   					String dateStr  =getValue(row,j++);
   					Date createDate=null;
					if(StringUtils.isNotBlank(dateStr)) {
						createDate= sdf.parse(dateStr);
						if(i==1) {
							params.put("createDate", createDate);
							 int count=expDailyScanService.selectByTime(params);
							 if(count>0) {
								 list.add(Constant.EXIST);
								 return list;
							 }
						}	
					}
					j++;//此处跳过收件业务员列
   					//收件人
   					String recipient = getValue(row,j++);
   					// 寄件人
   					String sender = getValue(row,j++);
   					// 网点称重
   					BigDecimal weight = new BigDecimal(getValue(row,j++));
   					//  重量来源
   					String weightSourse = getValue(row,j++);
   					// 数据来源
   					String dataSourse =getValue(row,j++);
   					// 设备编号
   					String deviceNumber = getValue(row,j++);
   					ExpDailyScanEntity entity=new ExpDailyScanEntity(waybillNumber, branch, person, createDate, recipient, sender, weight, weightSourse, dataSourse, deviceNumber, deptId);
   					list.add(entity);
		    } 
		    if(in!=null) in.close();
				} catch (IOException e) {
					e.printStackTrace();
					list.add(Constant.FILE_ERROR);
					return list;
				} 
		    return list; 
    }
       
       public  List getAllByExcel2003(String file) {
    	Long deptId = ShiroUtils.getUserEntity().getDeptId();//获取登录用的部门ID
   		List list = new ArrayList();
   		Map<String, Object> params=new HashMap<String, Object>();
   		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   		try {

   			Workbook rwb = Workbook.getWorkbook(new File(file));
   			Sheet rs = rwb.getSheet(0);// 或者rwb.getSheet(0)
   			int clos = rs.getColumns();// 得到所有的列
   			int rows = rs.getRows();// 得到所有的行
   			for (int i = 1; i < rows; i++) {
   					int j=0;
   					//运单号
   					String waybillNumber = rs.getCell(j++, i).getContents();
   					// 扫描网点
   					String branch = rs.getCell(j++, i).getContents();
   					// 扫描人
   					String person = rs.getCell(j++, i).getContents();
   					// 扫描时间
   					String dateStr  = rs.getCell(j++, i).getContents();
   					Date createDate=null;
					if(StringUtils.isNotBlank(dateStr)) {
						createDate= sdf.parse(dateStr);
						if(i==1) {
							params.put("createDate", createDate);
							 int count=expDailyScanService.selectByTime(params);
							 if(count>0) {
								 list.add(Constant.EXIST);
								 return list;
							 }
						}	
					}
					j++;//此处跳过收件业务员列
   					//收件人
   					String recipient = rs.getCell(j++, i).getContents();
   					// 寄件人
   					String sender = rs.getCell(j++, i).getContents();
   					// 网点称重
   					BigDecimal weight = new BigDecimal(rs.getCell(j++, i).getContents());
   					//  重量来源
   					String weightSourse = rs.getCell(j++, i).getContents();
   					// 数据来源
   					String dataSourse = rs.getCell(j++, i).getContents();
   					// 设备编号
   					String deviceNumber = rs.getCell(j++, i).getContents();
   					ExpDailyScanEntity entity=new ExpDailyScanEntity(waybillNumber, branch, person, createDate, recipient, sender, weight, weightSourse, dataSourse, deviceNumber, deptId);
   					list.add(entity);
   			}
   		} catch (Exception e) {
   			e.printStackTrace();
			list.add(Constant.FILE_ERROR);
			return list;
   		}
   		return list;

   	}
       
       public String getValue(XSSFRow row,int j){ 
         	XSSFCell cell=row.getCell(j); 
             int type=cell.getCellType(); 
             String s=""; 
             if(type==cell.CELL_TYPE_NUMERIC){ 
               if(HSSFDateUtil.isCellDateFormatted(cell)){ 
                 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
                 s=sdf.format(cell.getDateCellValue()); 
               }else { 
                 BigDecimal db = new BigDecimal(cell.getNumericCellValue()); 
                 s=String.valueOf(db); 
               } 
             }else if(type==cell.CELL_TYPE_STRING){ 
               s=cell.getStringCellValue(); 
             }else if(type==cell.CELL_TYPE_BOOLEAN){ 
               s=cell.getBooleanCellValue()+""; 
             }else if(type==cell.CELL_TYPE_FORMULA){ 
               s=cell.getCellFormula(); 
             }else if(type==cell.CELL_TYPE_BLANK){ 
               s=" "; 
             }else if(type==cell.CELL_TYPE_ERROR){ 
               s=" "; 
             }else{ 
                 
             } 
             return s.trim(); 
           } 


}
