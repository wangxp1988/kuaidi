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

import io.renren.modules.sys.entity.ExpPriceEntity;
import io.renren.modules.sys.service.ExpPriceService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



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
        expPriceService.insert(expPrice);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:expprice:update")
    public R update(@RequestBody ExpPriceEntity expPrice){
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

}
