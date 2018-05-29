package io.renren.modules.sys.controller;

import java.io.File;
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
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.renren.modules.sys.entity.ExpMoneyInOutEntity;
import io.renren.modules.sys.service.ExpMoneyInOutService;
import io.renren.modules.sys.shiro.ShiroUtils;
import jxl.Sheet;
import jxl.Workbook;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.utils.UploadAndExcelUtil;



/**
 * 收支
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-28 15:39:17
 */
@RestController
@RequestMapping("sys/expmoneyinout")
public class ExpMoneyInOutController {
    @Autowired
    private ExpMoneyInOutService expMoneyInOutService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:expmoneyinout:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = expMoneyInOutService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:expmoneyinout:info")
    public R info(@PathVariable("id") Long id){
        ExpMoneyInOutEntity expMoneyInOut = expMoneyInOutService.selectById(id);

        return R.ok().put("expMoneyInOut", expMoneyInOut);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:expmoneyinout:save")
    public R save(@RequestBody ExpMoneyInOutEntity expMoneyInOut){
        expMoneyInOutService.insert(expMoneyInOut);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:expmoneyinout:update")
    public R update(@RequestBody ExpMoneyInOutEntity expMoneyInOut){
        ValidatorUtils.validateEntity(expMoneyInOut);
        expMoneyInOutService.updateAllColumnById(expMoneyInOut);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:expmoneyinout:delete")
    public R delete(@RequestBody Long[] ids){
        expMoneyInOutService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    @RequestMapping("import")
  	public R Import(@RequestParam("file") MultipartFile file) throws IOException {
      	String filePath = UploadAndExcelUtil.saveFile(file);
      	List  list=getAllByExcel(filePath);
      	if(list.get(0).equals(Constant.EXIST)) {
    		return R.error("文件已经导入，不能重复导入");
    	}else if(list.get(0).equals(Constant.FILE_ERROR)) {
    		return R.error("文件或者文件版本错误，支持Excel 97-2003");
    	}
      	if (null != list) {
      		long startTime=System.currentTimeMillis(); 
  			int g=list.size()/100;
  			int j=0;
  			j=g;
  			if(list.size()%100>0){
  				j++;
  			}
  			if(j==g){
  				for (int i=0;i<g;i++) {
  					List<ExpMoneyInOutEntity> tempList=new ArrayList<ExpMoneyInOutEntity>();
  					tempList.addAll(list.subList(i*100, (i+1)*100));
  					expMoneyInOutService.saveList(tempList);
  				}
  			}else if(j>g){
  				for (int i=0;i<j;i++) {
  					List<ExpMoneyInOutEntity> tempList=new ArrayList<ExpMoneyInOutEntity>();
  					if(i<j-1){
  						tempList.addAll(list.subList(i*100, (i+1)*100));
  					}else if(i==j-1){
  						tempList.addAll(list.subList(i*100, (i*100+list.size()%100)));
  					}
  					expMoneyInOutService.saveList(tempList);
  				}
  			}
  			long endTime=System.currentTimeMillis(); //获取结束时间
  			System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
      	}
      	
      	return R.ok();
      }
      
      
      public List  getAllByExcel(String file) {
      	Long deptId = ShiroUtils.getUserEntity().getDeptId();//获取登录用的部门ID
  		List  list = new ArrayList ();
  		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  		Map<String, Object> params=new HashMap<String, Object>();
  		try {

  			Workbook rwb = Workbook.getWorkbook(new File(file));
  			Sheet rs = rwb.getSheet(0);// 或者rwb.getSheet(0)
  			int clos = rs.getColumns();// 得到所有的列
  			int rows = rs.getRows();// 得到所有的行
  			
  			List<String> colsNames=new ArrayList<String>();
  			for (int i = 0; i < rows; i++) {
  					int j=0;
  				    //先获取列名
  					if(i==0) {
  						//网点编号
  						for(int num=0;num<clos;num++) {
  							colsNames.add(rs.getCell(num, i).getContents());	
  						}
  						   
  					}else {
  					// 网点编码
  					String dotCode = rs.getCell(j++, i).getContents();
  					//网点名称
  					String dotName =rs.getCell(j++, i).getContents();
  					// 运单号
  					String waybillNumber = rs.getCell(j++, i).getContents();
  					// 时间
  					String dateStr  = rs.getCell(j++, i).getContents();
  					Date createDate =null;
					if(StringUtils.isNotBlank(dateStr)) {
						createDate= sdf.parse(dateStr);
						if(i==1) {
							params.put("createDate", createDate);
							 int count=expMoneyInOutService.selectByTime(params);
							 if(count>0) {
								 list.add(Constant.EXIST);
								 return list;
							 }
						}	
					}
  					int temclos=clos-1-j;
  					for(int tem=0;tem<temclos;tem++) {
  						String columnName=colsNames.get(j++).toString().trim();
  						if(!columnName.contains("合计")) {
  	  						String m=rs.getCell(j, i).getContents();
  	  						BigDecimal money=new BigDecimal(0);
  	  						if(StringUtils.isNoneBlank(m)) {
  	  							money=new BigDecimal(m);
  	  							if(money.signum()==-1) {
  	  								money=money.multiply(new BigDecimal(-1));
  	  							}
  	  						}
  	  					
  	  			         ExpMoneyInOutEntity entity=new ExpMoneyInOutEntity(waybillNumber, createDate, columnName, money, deptId);
  	  			         list.add(entity);
  						}
  					}
  					}
  			}
  		} catch (Exception e) {
  			e.printStackTrace();
			list.add(Constant.FILE_ERROR);
			return list;
  		}
  		return list;

  	}
      
      public static void main(String[] args) {
    	  String colsNames="收入合计";
    	  System.out.println(colsNames.contains("合计"));
	}
}
