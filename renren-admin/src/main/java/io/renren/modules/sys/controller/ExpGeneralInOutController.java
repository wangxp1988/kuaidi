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

import io.renren.modules.sys.entity.ExpGeneralInOutEntity;
import io.renren.modules.sys.service.ExpGeneralInOutService;
import io.renren.modules.sys.shiro.ShiroUtils;
import jxl.Sheet;
import jxl.Workbook;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.utils.UploadAndExcelUtil;



/**
 * 日常收支
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-25 09:29:17
 */
@RestController
@RequestMapping("sys/expgeneralinout")
public class ExpGeneralInOutController {
    @Autowired
    private ExpGeneralInOutService expGeneralInOutService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:expgeneralinout:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = expGeneralInOutService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:expgeneralinout:info")
    public R info(@PathVariable("id") Long id){
        ExpGeneralInOutEntity expGeneralInOut = expGeneralInOutService.selectById(id);

        return R.ok().put("expGeneralInOut", expGeneralInOut);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:expgeneralinout:save")
    public R save(@RequestBody ExpGeneralInOutEntity expGeneralInOut){
        expGeneralInOutService.insert(expGeneralInOut);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:expgeneralinout:update")
    public R update(@RequestBody ExpGeneralInOutEntity expGeneralInOut){
        ValidatorUtils.validateEntity(expGeneralInOut);
        expGeneralInOutService.updateAllColumnById(expGeneralInOut);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:expgeneralinout:delete")
    public R delete(@RequestBody Long[] ids){
        expGeneralInOutService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    @RequestMapping("import")
  	public R Import(@RequestParam("file") MultipartFile file) throws IOException {
      	String filePath = UploadAndExcelUtil.saveFile(file);
      	List<ExpGeneralInOutEntity> list=getAllByExcel(filePath);
      	
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
  					List<ExpGeneralInOutEntity> tempList=new ArrayList<ExpGeneralInOutEntity>();
  					tempList.addAll(list.subList(i*100, (i+1)*100));
  					expGeneralInOutService.saveList(tempList);
  				}
  			}else if(j>g){
  				for (int i=0;i<j;i++) {
  					List<ExpGeneralInOutEntity> tempList=new ArrayList<ExpGeneralInOutEntity>();
  					if(i<j-1){
  						tempList.addAll(list.subList(i*100, (i+1)*100));
  					}else if(i==j-1){
  						tempList.addAll(list.subList(i*100, (i*100+list.size()%100)));
  					}
  					expGeneralInOutService.saveList(tempList);
  				}
  			}
  			long endTime=System.currentTimeMillis(); //获取结束时间
  			System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
      	}
      	
      	return R.ok();
      }
      
      
      public static List<ExpGeneralInOutEntity> getAllByExcel(String file) {
      	Long deptId = ShiroUtils.getUserEntity().getDeptId();//获取登录用的部门ID
  		List<ExpGeneralInOutEntity> list = new ArrayList<ExpGeneralInOutEntity>();
  		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  		try {

  			Workbook rwb = Workbook.getWorkbook(new File(file));
  			Sheet rs = rwb.getSheet(0);// 或者rwb.getSheet(0)
  			int clos = rs.getColumns();// 得到所有的列
  			int rows = rs.getRows();// 得到所有的行
  			for (int i = 1; i < rows; i++) {
  					int j=0;
  					//订单号
  					Date createTime = sdf.parse(rs.getCell(j++, i).getContents());
  					// 客户编码
  					String customerId = rs.getCell(j++, i).getContents();
  					//运单号
  					String waybillNumber =rs.getCell(j++, i).getContents();
  					// 客户
  					String consumer = rs.getCell(j++, i).getContents();
  					// 款项说明
  					String moneyDetail = rs.getCell(j++, i).getContents();
  					// 收入金额
  					BigDecimal moneyIn = new BigDecimal(rs.getCell(j++, i).getContents());
  					//支出金额
  					String mo=rs.getCell(j++, i).getContents();
  					BigDecimal moneyOut=new BigDecimal(0) ;
  					if(StringUtils.isNotBlank(mo)) {
  						moneyOut = new BigDecimal( rs.getCell(j++, i).getContents());
  					}
  					// 账户
  					String account = rs.getCell(j++, i).getContents();
  					// 备注
  					String remarks = rs.getCell(j++, i).getContents();
  					ExpGeneralInOutEntity entity=new ExpGeneralInOutEntity(customerId, waybillNumber, consumer, moneyDetail, moneyIn, moneyOut, account, remarks, createTime, deptId);
  					list.add(entity);
  			}
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
  		return list;

  	}
}
