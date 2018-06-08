package io.renren.modules.sys.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.renren.common.validator.ValidatorUtils;

import org.apache.commons.lang3.StringUtils;
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
import io.renren.modules.sys.entity.ExpPriceEntity;
import io.renren.modules.sys.entity.PriceEntity;
import io.renren.modules.sys.service.ExpPriceService;
import io.renren.modules.sys.shiro.ShiroUtils;
import jxl.Sheet;
import jxl.Workbook;
import io.renren.common.utils.CityUtil;
import io.renren.common.utils.ExportExcel;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.utils.UploadAndExcelUtil;



/**
 * 价格表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-25 09:29:17
 */
@RestController
@RequestMapping("sys/expprice")
public class ExpPriceController {
	@Value("${filepath.excleSavePath}")
	private String diskDirPath;
    @Autowired
    private ExpPriceService expPriceService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:expprice:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = expPriceService.queryPage(params);

        return R.ok().put("page", page);
    }
    
    /**
     * 查询对于单位的价格表名称
     * @param params
     * @return
     */
    @RequestMapping("/listAllName")
    public R listAllName(@RequestParam Map<String, Object> params){
        List<ExpPriceEntity> list = expPriceService.listAllName(params);
        return R.ok().put("list", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:expprice:info")
    public R info(@PathVariable("id") Long id){
        ExpPriceEntity expPrice = expPriceService.selectById(id);

        return R.ok().put("expPrice", expPrice);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:expprice:save")
    public R save(@RequestBody ExpPriceEntity expPrice){
    	Long deptId = ShiroUtils.getUserEntity().getDeptId();//获取登录用的部门ID
    	expPrice.setDeptId(deptId);
        expPriceService.insert(expPrice);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:expprice:update")
    public R update(@RequestBody ExpPriceEntity expPrice){
    	Long deptId = ShiroUtils.getUserEntity().getDeptId();//获取登录用的部门ID
    	expPrice.setDeptId(deptId);
        ValidatorUtils.validateEntity(expPrice);
        expPriceService.updateAllColumnById(expPrice);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:expprice:delete")
    public R delete(@RequestBody Long[] ids){
    	expPriceService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }
    
    @RequestMapping("export")
    public void export(HttpServletRequest request,HttpServletResponse response,Map<String, Object> params) {
    	  //查询list
    	  List<ExpPriceEntity> list=expPriceService.selectAllDept(params);
    	  List<PriceEntity> plist=new ArrayList<PriceEntity>();
    	  list.forEach(item->{
    		  PriceEntity pi=new PriceEntity();
    		  pi.setId(item.getId());
    		  pi.setPriceName(item.getPriceName());
    		  pi.setProvinceName(item.getProvinceName());
    		  pi.setWeight(item.getWeight());
    		  pi.setMoney(item.getMoney());
    		  pi.setBaseId(item.getId());
    		  plist.add(pi);
    	  });
    	  String[] Title={"价格表ID","价格表名称","省份","重量","快递费","数据库ID(不要编辑)"}; 
    	  ExportExcel.exportExcel(response,"价格表资料信息.xls",Title, plist);  
    }
    
    /**
     * 
     * 导入
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping("import")
   	public R Import(@RequestParam("file") MultipartFile file) throws IOException {
       	String filePath = UploadAndExcelUtil.saveFile(file,diskDirPath);
       	List<ExpPriceEntity> list=getAllByExcel(filePath);
       	
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
   					List<ExpPriceEntity> tempList=new ArrayList<ExpPriceEntity>();
   					tempList.addAll(list.subList(i*100, (i+1)*100));
   					expPriceService.saveList(tempList);
   				}
   			}else if(j>g){
   				for (int i=0;i<j;i++) {
   					List<ExpPriceEntity> tempList=new ArrayList<ExpPriceEntity>();
   					if(i<j-1){
   						tempList.addAll(list.subList(i*100, (i+1)*100));
   					}else if(i==j-1){
   						tempList.addAll(list.subList(i*100, (i*100+list.size()%100)));
   					}
   					expPriceService.saveList(tempList);
   				}
   			}
   			long endTime=System.currentTimeMillis(); //获取结束时间
   			System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
       	}
       	
       	return R.ok();
       }
       
       
       public static List<ExpPriceEntity> getAllByExcel(String file) {
       	Long deptId = ShiroUtils.getUserEntity().getDeptId();//获取登录用的部门ID
   		List<ExpPriceEntity> list = new ArrayList<ExpPriceEntity>();
   		try {

   			Workbook rwb = Workbook.getWorkbook(new File(file));
   			Sheet rs = rwb.getSheet(0);// 或者rwb.getSheet(0)
   			int clos = rs.getColumns();// 得到所有的列
   			int rows = rs.getRows();// 得到所有的行
   			for (int i = 1; i < rows; i++) {
   					int j=0;
   				   //跳过第一列
   					j++;
   					// 价格表名称
   					String priceName = rs.getCell(j++, i).getContents();
   					// 省份名称
   					String provinceName =CityUtil.getCity(rs.getCell(j++, i).getContents()) ;
   					// 重量
   					BigDecimal weight = new BigDecimal(rs.getCell(j++, i).getContents());
   					// 价格
   					BigDecimal money =new BigDecimal( rs.getCell(j++, i).getContents());
   					Long id = null;
   					String isStr=rs.getCell(j++, i).getContents();
   					if(StringUtils.isNotBlank(isStr)) {
   						 id= new Long(isStr);
   					}
   					ExpPriceEntity entity=new ExpPriceEntity(id,priceName,provinceName, weight, money, deptId);
   					list.add(entity);
   			}
   		} catch (Exception e) {
   			e.printStackTrace();
   		}
   		return list;

   	}

}
