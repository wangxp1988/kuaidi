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
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.renren.modules.sys.entity.ExpBalanceAccountEntity;
import io.renren.modules.sys.entity.ExpOrderRookieEntity;
import io.renren.modules.sys.service.ExpBalanceAccountService;
import io.renren.modules.sys.shiro.ShiroUtils;
import jxl.Sheet;
import jxl.Workbook;
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
	public R Import(@RequestParam("file") MultipartFile file) throws IOException {
    	String filePath = UploadAndExcelUtil.saveFile(file);
    	List<ExpBalanceAccountEntity> list=getAllByExcel(filePath);
    	
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
    
    
    public static List<ExpBalanceAccountEntity> getAllByExcel(String file) {
    	Long deptId = ShiroUtils.getUserEntity().getDeptId();//获取登录用的部门ID
		List<ExpBalanceAccountEntity> list = new ArrayList<ExpBalanceAccountEntity>();
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
					Date sendTime = sdf.parse(rs.getCell(j++, i).getContents());
					// 寄件省份
					String sendProvince = rs.getCell(j++, i).getContents();
					//收件人
					String recipient = rs.getCell(j++, i).getContents();
					// 收件省份
					String recipientProvince = rs.getCell(j++, i).getContents();
					//揽件业务员
					String salesman = rs.getCell(j++, i).getContents();
					// 客户名称
					String customerName = rs.getCell(j++, i).getContents();
					// 客户手机号
					String customerPhone = rs.getCell(j++, i).getContents();
					// 实际重量
					BigDecimal actualWeight =new BigDecimal(rs.getCell(j++, i).getContents()) ;
					
					ExpBalanceAccountEntity entity=new ExpBalanceAccountEntity(waybillNumber, sender, branch, sendTime, sendProvince, recipient, recipientProvince, salesman, customerName, customerPhone, actualWeight, deptId);
					list.add(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

}
