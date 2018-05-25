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

import io.renren.modules.sys.entity.ExpCustomerEntity;
import io.renren.modules.sys.service.ExpCustomerService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 客户信息
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-25 09:29:17
 */
@RestController
@RequestMapping("sys/expcustomer")
public class ExpCustomerController {
    @Autowired
    private ExpCustomerService expCustomerService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:expcustomer:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = expCustomerService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:expcustomer:info")
    public R info(@PathVariable("id") Long id){
        ExpCustomerEntity expCustomer = expCustomerService.selectById(id);

        return R.ok().put("expCustomer", expCustomer);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:expcustomer:save")
    public R save(@RequestBody ExpCustomerEntity expCustomer){
        expCustomerService.insert(expCustomer);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:expcustomer:update")
    public R update(@RequestBody ExpCustomerEntity expCustomer){
        ValidatorUtils.validateEntity(expCustomer);
        expCustomerService.updateAllColumnById(expCustomer);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:expcustomer:delete")
    public R delete(@RequestBody Long[] ids){
        expCustomerService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
