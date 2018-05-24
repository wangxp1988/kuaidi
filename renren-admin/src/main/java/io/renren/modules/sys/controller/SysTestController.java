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

import io.renren.modules.sys.entity.SysTestEntity;
import io.renren.modules.sys.service.SysTestService;
import io.renren.modules.sys.shiro.ShiroUtils;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-22 15:17:59
 */
@RestController
@RequestMapping("sys/systest")
public class SysTestController {
    @Autowired
    private SysTestService sysTestService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:systest:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysTestService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:systest:info")
    public R info(@PathVariable("id") Long id){
        SysTestEntity sysTest = sysTestService.selectById(id);

        return R.ok().put("sysTest", sysTest);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:systest:save")
    public R save(@RequestBody SysTestEntity sysTest){
    	sysTest.setDeptId(ShiroUtils.getUserEntity().getDeptId());
    	sysTest.setUserId(ShiroUtils.getUserId());
        sysTestService.insert(sysTest);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:systest:update")
    public R update(@RequestBody SysTestEntity sysTest){
        ValidatorUtils.validateEntity(sysTest);
        sysTestService.updateAllColumnById(sysTest);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:systest:delete")
    public R delete(@RequestBody Long[] ids){
        sysTestService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
