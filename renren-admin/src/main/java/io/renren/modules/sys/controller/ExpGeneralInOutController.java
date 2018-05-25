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

import io.renren.modules.sys.entity.ExpGeneralInOutEntity;
import io.renren.modules.sys.service.ExpGeneralInOutService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



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

}
