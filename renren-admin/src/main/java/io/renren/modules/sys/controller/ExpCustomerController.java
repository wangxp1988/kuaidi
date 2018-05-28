package io.renren.modules.sys.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.renren.modules.sys.entity.ExpCustomerEntity;
import io.renren.modules.sys.entity.ExpGeneralInOutEntity;
import io.renren.modules.sys.service.ExpCustomerService;
import io.renren.modules.sys.shiro.ShiroUtils;
import jxl.Sheet;
import jxl.Workbook;
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
    
    
    @RequestMapping("import")
  	public R Import(@RequestParam("file") MultipartFile file) throws IOException {
      	String filePath = UploadAndExcelUtil.saveFile(file);
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
      
      
      public static List<ExpCustomerEntity> getAllByExcel(String file) {
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
  					ExpCustomerEntity entity=new ExpCustomerEntity(code, name, type, contacts, phone, address, priceName, paymentId, paymentName, deptId);
  					list.add(entity);
  			}
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
  		return list;

  	}

}
