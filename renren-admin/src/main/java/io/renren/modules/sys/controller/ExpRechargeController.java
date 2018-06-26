package io.renren.modules.sys.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.sys.entity.ExpRechargeEntity;
import io.renren.modules.sys.service.ExpRechargeService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 充值记录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-06-18 17:13:27
 */
@RestController
@RequestMapping("sys/exprecharge")
public class ExpRechargeController {
    @Autowired
    private ExpRechargeService expRechargeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:exprecharge:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = expRechargeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:exprecharge:info")
    public R info(@PathVariable("id") Long id){
        ExpRechargeEntity expRecharge = expRechargeService.selectById(id);

        return R.ok().put("expRecharge", expRecharge);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:exprecharge:save")
    public R save(Long userId,BigDecimal money,String userName,String availableDate){
    	 ExpRechargeEntity expRecharge=new ExpRechargeEntity();
    	expRecharge.setMoney(money);
    	expRecharge.setUserName(userName);
    	expRecharge.setUserId(userId);
    	expRecharge.setCreateDate(new Date());
        expRechargeService.saveR(expRecharge,availableDate);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:exprecharge:update")
    public R update(@RequestBody ExpRechargeEntity expRecharge){
        ValidatorUtils.validateEntity(expRecharge);
        expRechargeService.updateAllColumnById(expRecharge);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:exprecharge:delete")
    public R delete(@RequestBody Long[] ids){
        expRechargeService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
