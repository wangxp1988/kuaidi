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

import io.renren.modules.sys.entity.ExpCustomerTypeEntity;
import io.renren.modules.sys.service.ExpCustomerTypeService;
import io.renren.modules.sys.shiro.ShiroUtils;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 客户类型
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-28 11:36:06
 */
@RestController
@RequestMapping("sys/expcustomertype")
public class ExpCustomerTypeController {
    @Autowired
    private ExpCustomerTypeService expCustomerTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:expcustomertype:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = expCustomerTypeService.queryPage(params);

        return R.ok().put("page", page);
    }
    
    
    @RequestMapping("/listAll")
    public R listAll(@RequestParam Map<String, Object> params){
        List<ExpCustomerTypeEntity> list = expCustomerTypeService.listAll(params);
        return R.ok().put("list", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:expcustomertype:info")
    public R info(@PathVariable("id") Long id){
        ExpCustomerTypeEntity expCustomerType = expCustomerTypeService.selectById(id);

        return R.ok().put("expCustomerType", expCustomerType);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:expcustomertype:save")
    public R save(@RequestBody ExpCustomerTypeEntity expCustomerType){
    	Long deptId = ShiroUtils.getUserEntity().getDeptId();//获取登录用的部门ID
    	expCustomerType.setDeptId(deptId);
        expCustomerTypeService.insert(expCustomerType);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:expcustomertype:update")
    public R update(@RequestBody ExpCustomerTypeEntity expCustomerType){
    	Long deptId = ShiroUtils.getUserEntity().getDeptId();//获取登录用的部门ID
    	expCustomerType.setDeptId(deptId);
        ValidatorUtils.validateEntity(expCustomerType);
        expCustomerTypeService.updateAllColumnById(expCustomerType);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:expcustomertype:delete")
    public R delete(@RequestBody Long[] ids){
        expCustomerTypeService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
