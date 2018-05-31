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

import io.renren.modules.sys.entity.ExpOrdersEntity;
import io.renren.modules.sys.service.ExpOrdersService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 中转数据表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-31 09:24:05
 */
@RestController
@RequestMapping("sys/exporders")
public class ExpOrdersController {
    @Autowired
    private ExpOrdersService expOrdersService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:exporders:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = expOrdersService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:exporders:info")
    public R info(@PathVariable("id") Long id){
        ExpOrdersEntity expOrders = expOrdersService.selectById(id);

        return R.ok().put("expOrders", expOrders);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:exporders:save")
    public R save(@RequestBody ExpOrdersEntity expOrders){
        expOrdersService.insert(expOrders);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:exporders:update")
    public R update(@RequestBody ExpOrdersEntity expOrders){
        ValidatorUtils.validateEntity(expOrders);
        expOrdersService.updateAllColumnById(expOrders);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:exporders:delete")
    public R delete(@RequestBody Long[] ids){
        expOrdersService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
