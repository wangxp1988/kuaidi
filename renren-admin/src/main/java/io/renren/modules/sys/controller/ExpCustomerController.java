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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import io.renren.modules.sys.entity.CustomerEntity;
import io.renren.modules.sys.entity.ExpBalanceAccountEntity;
import io.renren.modules.sys.entity.ExpCustomerEntity;
import io.renren.modules.sys.service.ExpCustomerService;
import io.renren.modules.sys.shiro.ShiroUtils;
import jxl.Sheet;
import jxl.Workbook;
import io.renren.common.utils.Constant;
import io.renren.common.utils.ExportExcel;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.utils.UploadAndExcelUtil;



/**
 * 客户信息
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-27 20:22:52
 */
@RestController
@RequestMapping("sys/expcustomer")
public class ExpCustomerController {
	@Value("${filepath.excleSavePath}")
	private  String diskDirPath;
    @Autowired
    private ExpCustomerService expCustomerService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:expcustomer:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = expCustomerService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 付款用户加载列表
     */
    @RequestMapping("/listAllCustomer")
    public R listAllCustomer(@RequestParam Map<String, Object> params){
        List<ExpCustomerEntity> list = expCustomerService.listAllCustomer(params);
        return R.ok().put("list", list);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:expcustomer:info")
    public R info(@PathVariable("id") Long id){
        ExpCustomerEntity expCustomer = expCustomerService.selectById(id);

        return R.ok().put("expCustomer", expCustomer);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:expcustomer:save")
    public R save(@RequestBody ExpCustomerEntity expCustomer){
    	Long deptId = ShiroUtils.getUserEntity().getDeptId();//获取登录用的部门ID
    	expCustomer.setDeptId(deptId);
        expCustomerService.insert(expCustomer);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:expcustomer:update")
    public R update(@RequestBody ExpCustomerEntity expCustomer){
    	Long deptId = ShiroUtils.getUserEntity().getDeptId();//获取登录用的部门ID
    	expCustomer.setDeptId(deptId);
        ValidatorUtils.validateEntity(expCustomer);
        expCustomerService.updateAllColumnById(expCustomer);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:expcustomer:delete")
    public R delete(@RequestBody Long[] ids){
        expCustomerService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }
    @RequestMapping("export")
    public void export(HttpServletRequest request,HttpServletResponse response,Map<String, Object> params) {
    	List<ExpCustomerEntity> list=expCustomerService.selectAllDept(params);
    	List<CustomerEntity> cl=new ArrayList<CustomerEntity>();
    	list.forEach(item->{
    		CustomerEntity ce=new CustomerEntity();
    		ce.setId(item.getId());
    		ce.setCode(item.getCode());
    		ce.setName(item.getName());
    		ce.setType(item.getType());
    		ce.setContacts(item.getContacts());
    		ce.setPhone(item.getPhone());
    		ce.setAddress(item.getAddress());
    		ce.setPriceName(item.getPriceName());
            ce.setPaymentId(item.getPaymentId());
            ce.setPaymentName(item.getPaymentName());
            ce.setBaseId(item.getId());
            cl.add(ce);
    	});
    	String[] Title={"内部ID","客户编号","客户名称","客户类型","联系人","联系电话","地址","价格表名称","付款客户ID","付款客户名称","数据库ID(不要编辑)"}; 
  	  ExportExcel.exportExcel(response,"客户资料信息.xls",Title, cl);  
    
    }
    
    @RequestMapping("import")
  	public R Import(@RequestParam("file") MultipartFile file) throws Exception {
      	String filePath = UploadAndExcelUtil.saveFile(file,diskDirPath);
      	List<ExpCustomerEntity> list=getAllByExcel(filePath);
      	
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
  					List<ExpCustomerEntity> tempList=new ArrayList<ExpCustomerEntity>();
  					tempList.addAll(list.subList(i*100, (i+1)*100));
  					expCustomerService.saveList(tempList);
  				}
  			}else if(j>g){
  				for (int i=0;i<j;i++) {
  					List<ExpCustomerEntity> tempList=new ArrayList<ExpCustomerEntity>();
  					if(i<j-1){
  						tempList.addAll(list.subList(i*100, (i+1)*100));
  					}else if(i==j-1){
  						tempList.addAll(list.subList(i*100, (i*100+list.size()%100)));
  					}
  					expCustomerService.saveList(tempList);
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
		     int j=1;
				//客户编码
					String code = getValue(row,j++);
					// 客户名称
					String name =getValue(row,j++);
					//客户类型
					String type =getValue(row,j++);
					//联系人
					String contacts =getValue(row,j++);
					//电话
					String phone =getValue(row,j++);
					//地址
					String address =getValue(row,j++);
					// 价格表名称
					String priceName =getValue(row,j++);
					// 付款客户ID
					String paymentId = getValue(row,j++);
					// 付款客户名称
					String paymentName = getValue(row,j++);
					Long id = null;
					String isStr=getValue(row,j++);
					if(StringUtils.isNotBlank(isStr)) {
						 id= new Long(isStr);
					}
					ExpCustomerEntity entity=new ExpCustomerEntity(id,code, name, type, contacts, phone, address, priceName, paymentId, paymentName, deptId);
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
      public static List<ExpCustomerEntity> getAllByExcel2003(String file) {
      	Long deptId = ShiroUtils.getUserEntity().getDeptId();//获取登录用的部门ID
  		List<ExpCustomerEntity> list = new ArrayList<ExpCustomerEntity>();
  		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  		try {
 
  			Workbook rwb = Workbook.getWorkbook(new File(file));
  			Sheet rs = rwb.getSheet(0);// 或者rwb.getSheet(0)
  			int clos = rs.getColumns();// 得到所有的列
  			int rows = rs.getRows();// 得到所有的行
  			for (int i = 1; i < rows; i++) {
  					int j=1;
  					//客户编码
  					String code =  rs.getCell(j++, i).getContents();
  					// 客户名称
  					String name = rs.getCell(j++, i).getContents();
  					//客户类型
  					String type =rs.getCell(j++, i).getContents();
  					//联系人
  					String contacts =rs.getCell(j++, i).getContents();
  					//电话
  					String phone =rs.getCell(j++, i).getContents();
  					//地址
  					String address =rs.getCell(j++, i).getContents();
  					// 价格表名称
  					String priceName = rs.getCell(j++, i).getContents();
  					// 付款客户ID
  					String paymentId = rs.getCell(j++, i).getContents();
  					// 付款客户名称
  					String paymentName = rs.getCell(j++, i).getContents();
  					Long id = null;
   					String isStr=rs.getCell(j++, i).getContents();
   					if(StringUtils.isNotBlank(isStr)) {
   						 id= new Long(isStr);
   					}
  					ExpCustomerEntity entity=new ExpCustomerEntity(id,code, name, type, contacts, phone, address, priceName, paymentId, paymentName, deptId);
  					list.add(entity);
  			}
  		} catch (Exception e) {
  			e.printStackTrace();
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
