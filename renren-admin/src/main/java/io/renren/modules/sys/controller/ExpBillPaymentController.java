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

import io.renren.modules.sys.entity.ExpBillPaymentEntity;
import io.renren.modules.sys.service.ExpBillPaymentService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 账单收支
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-06-27 15:27:46
 */
@RestController
@RequestMapping("sys/expbillpayment")
public class ExpBillPaymentController {
    @Autowired
    private ExpBillPaymentService expBillPaymentService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:expbillpayment:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = expBillPaymentService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:expbillpayment:info")
    public R info(@PathVariable("id") Long id){
        ExpBillPaymentEntity expBillPayment = expBillPaymentService.selectById(id);

        return R.ok().put("expBillPayment", expBillPayment);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:expbillpayment:save")
    public R save(@RequestBody ExpBillPaymentEntity expBillPayment){
        expBillPaymentService.insert(expBillPayment);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:expbillpayment:update")
    public R update(@RequestBody ExpBillPaymentEntity expBillPayment){
        ValidatorUtils.validateEntity(expBillPayment);
        expBillPaymentService.updateAllColumnById(expBillPayment);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:expbillpayment:delete")
    public R delete(@RequestBody Long[] ids){
        expBillPaymentService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
