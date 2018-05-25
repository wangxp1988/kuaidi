package io.renren.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.sys.entity.ExpBalanceAccountEntity;
import io.renren.modules.sys.service.ExpBalanceAccountService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



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

}
