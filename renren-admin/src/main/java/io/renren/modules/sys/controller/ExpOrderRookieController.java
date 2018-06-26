package io.renren.modules.sys.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
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
import org.apache.poi.ss.usermodel.Cell;
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


import io.renren.modules.sys.entity.ExpOrderRookieEntity;
import io.renren.modules.sys.entity.ExpTempEntity;
import io.renren.modules.sys.service.ExpOrderRookieService;
import io.renren.modules.sys.service.ExpTempService;
import io.renren.modules.sys.shiro.ShiroUtils;
import jxl.Sheet;
import jxl.Workbook;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.utils.UploadAndExcelUtil;



/**
 * 菜鸟订单
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-25 09:29:17
 */
@RestController
@RequestMapping("sys/exporderrookie")
public class ExpOrderRookieController {
	@Value("${filepath.excleSavePath}")
	private  String diskDirPath;
    @Autowired
    private ExpOrderRookieService expOrderRookieService;
    @Autowired
    private ExpTempService expTempService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:exporderrookie:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = expOrderRookieService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:exporderrookie:info")
    public R info(@PathVariable("id") Long id){
        ExpOrderRookieEntity expOrderRookie = expOrderRookieService.selectById(id);

        return R.ok().put("expOrderRookie", expOrderRookie);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:exporderrookie:save")
    public R save(@RequestBody ExpOrderRookieEntity expOrderRookie){
        expOrderRookieService.insert(expOrderRookie);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:exporderrookie:update")
    public R update(@RequestBody ExpOrderRookieEntity expOrderRookie){
        ValidatorUtils.validateEntity(expOrderRookie);
        expOrderRookieService.updateAllColumnById(expOrderRookie);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:exporderrookie:delete")
    public R delete(@RequestBody Long[] ids){
        expOrderRookieService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }
    
    @RequestMapping("import")
	public R Import(@RequestParam("file") MultipartFile file) throws Exception {
    	String filePath = UploadAndExcelUtil.saveFile(file,diskDirPath);
    	List list=getAllByExcel(filePath);
    	String dates="";
    	if(list.get(0).equals(Constant.EXIST)) {
    		return R.error("文件已经导入，不能重复导入");
    	}else if(list.get(0).equals(Constant.FILE_ERROR)) {
    		return R.error("文件错误，请检测");
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
					List<ExpOrderRookieEntity> tempList=new ArrayList<ExpOrderRookieEntity>();
					tempList.addAll(list.subList(i*100, (i+1)*100));
					expOrderRookieService.saveList(tempList);
					if(i==0) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						dates=sdf.format(tempList.get(0).getCreateDate());
					}
				}
			}else if(j>g){
				for (int i=0;i<j;i++) {
					List<ExpOrderRookieEntity> tempList=new ArrayList<ExpOrderRookieEntity>();
					if(i<j-1){
						tempList.addAll(list.subList(i*100, (i+1)*100));
					}else if(i==j-1){
						tempList.addAll(list.subList(i*100, (i*100+list.size()%100)));
					}
					expOrderRookieService.saveList(tempList);
					if(i==0) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						dates=sdf.format(tempList.get(0).getCreateDate());
					}
				}
			}
			
			
			long endTime=System.currentTimeMillis(); //获取结束时间
			System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
			
			//保存中转表
			 
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("dates",dates);
			List<ExpTempEntity> lists=expTempService.selectFromRookieByDate(params);
			this.batchSaveTemp(list);
    	}
    	
    	return R.ok();
    }
    
    /**
	  * 批量中转保存凭证
	  * @param list
	  */
	 public void batchSaveTemp(List<ExpTempEntity> list) {
		 if (null != list) {
				int g=list.size()/100;
				int j=0;
				j=g;
				if(list.size()%100>0){
					j++;
				}
				System.out.println("j=="+j);
				if(j==g){
					for (int i=0;i<g;i++) {
						List<ExpTempEntity> tempList=new ArrayList<ExpTempEntity>();
						tempList.addAll(list.subList(i*100, (i+1)*100));
						expTempService.saveList(tempList);
					}
				}else if(j>g){
					for (int i=0;i<j;i++) {
						List<ExpTempEntity> tempList=new ArrayList<ExpTempEntity>();
						if(i<j-1){
							tempList.addAll(list.subList(i*100, (i+1)*100));
						}else if(i==j-1){
							tempList.addAll(list.subList(i*100, (i*100+list.size()%100)));
						}
						expTempService.saveList(tempList);
					}
				}
	    	} 
	 }
    
    public  List getAllByExcel(String file) throws Exception{
    	List<Object[]> list=new ArrayList<Object[]>(); 
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
		    
		     /*for(int j=0;j<cells;j++){ 
		        XSSFCell cell=row.getCell(j); 
		        Object obj=null; 
		        if(cell!=null){ 
		          obj=getValue(cell); 
		        } 
		        csr[j]=obj; 
		      } */
		   //订单号
				String orderNumber =getValue(row,j++);
				// 运单号
				String waybillNumber =getValue(row,j++);
				// 创建时间
				String dateStr  = getValue(row,j++);
				Date createDate =null;
				if(StringUtils.isNotBlank(dateStr)) {
					createDate= sdf.parse(dateStr);
					if(i==1) {
						params.put("createDate", createDate);
						 int count=expOrderRookieService.selectByTime(params);
						 if(count>0) {
							 list.add(Constant.EXIST);
							 return list;
						 }
					}	
				}
				// 订单状态
				String orderStatus = getValue(row,j++);
				// 订单来源
				String orderSoruce = getValue(row,j++);
				// 网点编号
				String dotCode = getValue(row,j++);
				// 网点名称
				String dotName = getValue(row,j++);
				// 客户编号
				String customerCode = getValue(row,j++);
				// 客户名称
				String customerName =getValue(row,j++);
				if(StringUtils.isNotBlank(customerName)) {
					customerName=customerName.replace(dotName, "");
				}
				// 目的网点
				String destinationDot = getValue(row,j++);
				// 目的分拨
				String objectiveAllocation = getValue(row,j++);
				// 目的省份
				String destinationProvince = getValue(row,j++);
				// 目的市
				String destinationCity = getValue(row,j++);
				// 目的区
				String destinationArea =getValue(row,j++);
				// 收件地址
				String address =getValue(row,j++);
				ExpOrderRookieEntity entity=new ExpOrderRookieEntity(orderNumber, waybillNumber, createDate, orderStatus, orderSoruce, dotCode, dotName, customerCode, customerName, destinationDot, objectiveAllocation, destinationProvince, destinationCity, destinationArea, address, deptId);
		         list.add(entity); 
		    } 
		    if(in!=null) in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
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
					//订单号
					String orderNumber = rs.getCell(j++, i).getContents();
					// 运单号
					String waybillNumber = rs.getCell(j++, i).getContents();
					// 创建时间
					String dateStr  = rs.getCell(j++, i).getContents();
  					Date createDate =null;
					if(StringUtils.isNotBlank(dateStr)) {
						createDate= sdf.parse(dateStr);
						if(i==1) {
							params.put("createDate", createDate);
							 int count=expOrderRookieService.selectByTime(params);
							 if(count>0) {
								 list.add(Constant.EXIST);
								 return list;
							 }
						}	
					}
					// 订单状态
					String orderStatus = rs.getCell(j++, i).getContents();
					// 订单来源
					String orderSoruce = rs.getCell(j++, i).getContents();
					// 网点编号
					String dotCode = rs.getCell(j++, i).getContents();
					// 网点名称
					String dotName = rs.getCell(j++, i).getContents();
					// 客户编号
					String customerCode = rs.getCell(j++, i).getContents();
					// 客户名称
					String customerName = rs.getCell(j++, i).getContents();
					if(StringUtils.isNotBlank(customerName)) {
						customerName=customerName.replace(dotName, "");
					}
					// 目的网点
					String destinationDot = rs.getCell(j++, i).getContents();
					// 目的分拨
					String objectiveAllocation = rs.getCell(j++, i).getContents();
					// 目的省份
					String destinationProvince = rs.getCell(j++, i).getContents();
					// 目的市
					String destinationCity = rs.getCell(j++, i).getContents();
					// 目的区
					String destinationArea = rs.getCell(j++, i).getContents();
					// 收件地址
					String address = rs.getCell(j++, i).getContents();
					ExpOrderRookieEntity entity=new ExpOrderRookieEntity(orderNumber, waybillNumber, createDate, orderStatus, orderSoruce, dotCode, dotName, customerCode, customerName, destinationDot, objectiveAllocation, destinationProvince, destinationCity, destinationArea, address, deptId);
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
