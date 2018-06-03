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

import io.renren.modules.sys.entity.ExpVoucherEntity;
import io.renren.modules.sys.service.ExpVoucherService;
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

}
