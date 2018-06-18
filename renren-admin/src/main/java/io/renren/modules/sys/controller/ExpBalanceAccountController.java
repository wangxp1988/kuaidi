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

import io.renren.modules.sys.entity.ExpBalanceAccountEntity;
import io.renren.modules.sys.service.ExpBalanceAccountService;
import io.renren.modules.sys.shiro.ShiroUtils;
import jxl.Sheet;
import jxl.Workbook;
import io.renren.common.utils.CityUtil;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.utils.UploadAndExcelUtil;



/**
 * 每日对账表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-25 09:29:17
 */
@RestController
@RequestMapping("sys/expbalanceaccount")
public class ExpBalanceAccountController {
	
	@Value("${filepath.excleSavePath}")
	private String diskDirPath;
    @Autowired
    private ExpBalanceAccountService expBalanceAccountService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:expbalanceaccount:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = expBalanceAccountService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:expbalanceaccount:info")
    public R info(@PathVariable("id") Long id){
        ExpBalanceAccountEntity expBalanceAccount = expBalanceAccountService.selectById(id);

        return R.ok().put("expBalanceAccount", expBalanceAccount);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:expbalanceaccount:save")
    public R save(@RequestBody ExpBalanceAccountEntity expBalanceAccount){
        expBalanceAccountService.insert(expBalanceAccount);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:expbalanceaccount:update")
    public R update(@RequestBody ExpBalanceAccountEntity expBalanceAccount){
        ValidatorUtils.validateEntity(expBalanceAccount);
        expBalanceAccountService.updateAllColumnById(expBalanceAccount);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:expbalanceaccount:delete")
    public R delete(@RequestBody Long[] ids){
        expBalanceAccountService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }
    
    
    @RequestMapping("import")
	public R Import(@RequestParam("file") MultipartFile file) throws Exception {
    	String filePath = UploadAndExcelUtil.saveFile(file,diskDirPath);
    	List list=getAllByExcel(filePath);
    	if(list.get(0).equals(Constant.EXIST)) {
    		return R.error("文件已经导入，不能重复导入");
    	}else if(list.get(0).equals(Constant.FILE_ERROR)) {
    		return R.error("文件错误请检查");
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
					List<ExpBalanceAccountEntity> tempList=new ArrayList<ExpBalanceAccountEntity>();
					tempList.addAll(list.subList(i*100, (i+1)*100));
					expBalanceAccountService.saveList(tempList);
				}
			}else if(j>g){
				for (int i=0;i<j;i++) {
					List<ExpBalanceAccountEntity> tempList=new ArrayList<ExpBalanceAccountEntity>();
					if(i<j-1){
						tempList.addAll(list.subList(i*100, (i+1)*100));
					}else if(i==j-1){
						tempList.addAll(list.subList(i*100, (i*100+list.size()%100)));
					}
					expBalanceAccountService.saveList(tempList);
				}
			}
			long endTime=System.currentTimeMillis(); //获取结束时间
			System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
    	}
    	
    	return R.ok();
    }
    
    public  List getAllByExcel(String file) throws Exception{
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
				// 运单号
				String waybillNumber =getValue(row,j++);
				//寄件人
				String sender = getValue(row,j++);
				// 网点
				String branch = getValue(row,j++);
				// 创建时间
				String dateStr=getValue(row,j++);
				Date sendTime=null;
				if(StringUtils.isNotBlank(dateStr)) {
					sendTime= sdf.parse(dateStr);
					if(i==1) {
						params.put("sendTime", sendTime);
						 int count=expBalanceAccountService.selectByTime(params);
						 if(count>0) {
							 list.add(Constant.EXIST);
							 return list;
						 }
					}	
				}
				
				// 寄件省份
				String sendProvince = getValue(row,j++);
				//收件人
				String recipient =getValue(row,j++);
				// 收件省份
				String recipientProvince = getValue(row,j++);
				//揽件业务员
				String salesman = getValue(row,j++);
				// 客户名称
				String customerName = getValue(row,j++);
				// 客户手机号
				String customerPhone =getValue(row,j++);
				// 实际重量
				String actualWeightStr=getValue(row,j++);
				BigDecimal actualWeight=new BigDecimal(0);
				if(StringUtils.isNotBlank(actualWeightStr)) {
					 actualWeight =new BigDecimal(actualWeightStr) ;
				}
				ExpBalanceAccountEntity entity=new ExpBalanceAccountEntity(waybillNumber, sender, branch, sendTime, sendProvince, recipient, recipientProvince, salesman, customerName, customerPhone, actualWeight, deptId);
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
    
    public   List<ExpBalanceAccountEntity> getAllByExcel2003(String file) {
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
					
					// 运单号
					String waybillNumber = rs.getCell(j++, i).getContents();
					//寄件人
					String sender = rs.getCell(j++, i).getContents();
					// 网点
					String branch = rs.getCell(j++, i).getContents();
					// 创建时间
					String dateStr=rs.getCell(j++, i).getContents();
					Date sendTime=null;
					if(StringUtils.isNotBlank(dateStr)) {
						sendTime= sdf.parse(dateStr);
						if(i==1) {
							params.put("sendTime", sendTime);
							 int count=expBalanceAccountService.selectByTime(params);
							 if(count>0) {
								 list.add(Constant.EXIST);
								 return list;
							 }
						}	
					}
					
					// 寄件省份
					String sendProvince = CityUtil.getCity(rs.getCell(j++, i).getContents());
					//收件人
					String recipient = rs.getCell(j++, i).getContents();
					// 收件省份
					String recipientProvince = CityUtil.getCity(rs.getCell(j++, i).getContents());
					//揽件业务员
					String salesman = rs.getCell(j++, i).getContents();
					// 客户名称
					String customerName = rs.getCell(j++, i).getContents();
					// 客户手机号
					String customerPhone = rs.getCell(j++, i).getContents();
					// 实际重量
					String actualWeightStr=rs.getCell(j++, i).getContents();
					BigDecimal actualWeight=new BigDecimal(0);
					if(StringUtils.isNotBlank(actualWeightStr)) {
						 actualWeight =new BigDecimal(actualWeightStr) ;
					}
					ExpBalanceAccountEntity entity=new ExpBalanceAccountEntity(waybillNumber, sender, branch, sendTime, sendProvince, recipient, recipientProvince, salesman, customerName, customerPhone, actualWeight, deptId);
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
