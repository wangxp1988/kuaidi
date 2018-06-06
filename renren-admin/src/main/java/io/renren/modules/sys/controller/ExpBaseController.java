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

import io.renren.modules.sys.entity.ExpBaseEntity;
import io.renren.modules.sys.service.ExpBaseService;
import io.renren.modules.sys.shiro.ShiroUtils;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 基础数据
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-06-06 09:34:11
 */
@RestController
@RequestMapping("sys/expbase")
public class ExpBaseController {
    @Autowired
    private ExpBaseService expBaseService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:expbase:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = expBaseService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:expbase:info")
    public R info(@PathVariable("id") Long id){
        ExpBaseEntity expBase = expBaseService.selectById(id);

        return R.ok().put("expBase", expBase);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:expbase:save")
    public R save(@RequestBody ExpBaseEntity expBase){
    	Long deptId = ShiroUtils.getUserEntity().getDeptId();//获取登录用的部门ID
    	expBase.setDeptId(deptId);
        expBaseService.insert(expBase);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:expbase:update")
    public R update(@RequestBody ExpBaseEntity expBase){
    	Long deptId = ShiroUtils.getUserEntity().getDeptId();//获取登录用的部门ID
    	expBase.setDeptId(deptId);
        ValidatorUtils.validateEntity(expBase);
        expBaseService.updateAllColumnById(expBase);//全部更新
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:expbase:delete")
    public R delete(@RequestBody Long[] ids){
        expBaseService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
