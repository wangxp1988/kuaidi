package io.renren.modules.sys.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.sys.entity.ExpPriceEntity;
import io.renren.modules.sys.entity.ExpVoucherEntity;
import io.renren.modules.sys.entity.PriceEntity;
import io.renren.modules.sys.entity.VoucherEntity;
import io.renren.modules.sys.service.ExpVoucherService;
import io.renren.common.utils.ExportExcel;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 凭证管理
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-06-03 12:07:07
 */
@RestController
@RequestMapping("sys/expvoucher")
public class ExpVoucherController {
    @Autowired
    private ExpVoucherService expVoucherService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:expvoucher:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = expVoucherService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:expvoucher:info")
    public R info(@PathVariable("id") Long id){
        ExpVoucherEntity expVoucher = expVoucherService.selectById(id);

        return R.ok().put("expVoucher", expVoucher);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:expvoucher:save")
    public R save(@RequestBody ExpVoucherEntity expVoucher){
        expVoucherService.insert(expVoucher);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:expvoucher:update")
    public R update(@RequestBody ExpVoucherEntity expVoucher){
        ValidatorUtils.validateEntity(expVoucher);
        expVoucherService.updateAllColumnById(expVoucher);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:expvoucher:delete")
    public R delete(@RequestBody Long[] ids){
        expVoucherService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }
    
    @RequestMapping("export")
    public void export(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> params) {
    	//查询list
    	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	  List<ExpVoucherEntity> list=expVoucherService.selectAllDept(params);
    	  List<VoucherEntity> plist=new ArrayList<VoucherEntity>();
    	  list.forEach(item->{
    		  VoucherEntity pi=new VoucherEntity();
			  pi.setId(item.getId());
    		  pi.setVoucherRemark(item.getVoucherRemark());
    		  pi.setVoucherCode(item.getVoucherCode());
    		  pi.setTwoLevelCoding(item.getTwoLevelCoding());
    		  pi.setTwoLevelName(item.getTwoLevelName());
    		  pi.setCustomerName(item.getCustomerName());
    		  pi.setWaybillNumber(item.getWaybillNumber());
    		  pi.setDestinationDot(item.getDestinationDot());
    		  pi.setDebtorMoney(item.getDebtorMoney());
    		  pi.setLenderMoney(item.getLenderMoney());
    		  pi.setDebtorWeight(item.getDebtorWeight());
    		  pi.setLenderWeight(item.getLenderWeight());
    		  pi.setCustomerCode(item.getCustomerCode());
    		  pi.setCreateDate(sdf.format(item.getCreateDate()));
    		  plist.add(pi);
    	  });
    	  String[] Title={"ID","凭证摘要","凭证编码","二级编码","二级名称","客户名称","运单号","目的网点",
    			  "借方金额","贷方金额","借方重量","贷方重量","客户编码","创建时间"}; 
    	  ExportExcel.exportExcel(response,params.get("dates")+"凭证信息.xls",Title, plist);  
    }

}
