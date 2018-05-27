package io.renren.modules.sys.controller;

import java.util.Arrays;
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

import io.renren.modules.sys.entity.SysAreaEntity;
import io.renren.modules.sys.service.SysAreaService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 行政区划表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-26 21:56:55
 */
@RestController
@RequestMapping("sys/sysarea")
public class SysAreaController {
    @Autowired
    private SysAreaService sysAreaService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:sysarea:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysAreaService.queryPage(params);

        return R.ok().put("page", page);
    }
    
    /**
     * 列表
     */
    @RequestMapping("/listAll")
    public R listProvince(@RequestParam Map<String, Object> params){
    	Long parentId=-1L;
        List<SysAreaEntity> list= sysAreaService.selectProvinceList(parentId);
        return R.ok().put("list", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:sysarea:info")
    public R info(@PathVariable("id") Long id){
        SysAreaEntity sysArea = sysAreaService.selectById(id);

        return R.ok().put("sysArea", sysArea);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:sysarea:save")
    public R save(@RequestBody SysAreaEntity sysArea){
        sysAreaService.insert(sysArea);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:sysarea:update")
    public R update(@RequestBody SysAreaEntity sysArea){
        ValidatorUtils.validateEntity(sysArea);
        sysAreaService.updateAllColumnById(sysArea);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:sysarea:delete")
    public R delete(@RequestBody Long[] ids){
        sysAreaService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
