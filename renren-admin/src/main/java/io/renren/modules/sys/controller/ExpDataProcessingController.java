package io.renren.modules.sys.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.R;
import io.renren.modules.sys.entity.ExpCustomerEntity;
import io.renren.modules.sys.entity.ExpOrdersEntity;
import io.renren.modules.sys.service.ExpBalanceAccountService;
import io.renren.modules.sys.service.ExpBaseService;
import io.renren.modules.sys.service.ExpCustomerService;
import io.renren.modules.sys.service.ExpDailyScanService;
import io.renren.modules.sys.service.ExpDataProcessingService;
import io.renren.modules.sys.service.ExpOrderRookieService;
import io.renren.modules.sys.service.ExpOrdersService;
import io.renren.modules.sys.service.ExpPriceService;

/**
 * 数据处理控制器
 * 
 * @author Administrator 
 * 上传完毕后：
 *  1、检查今日扫描中有的运单号 菜鸟中无的，我要到对账单中 查找到相应的运单号及信息
 *   2、然后再对比客户信息表 查出 新的客户 
 *   3、将重量加上 自定义的数量 
 *   4、利用价格表 计算出运费收入
 *   5、然后生成相应的凭证
 */
@RestController
@RequestMapping("sys/expdailyscan")
public class ExpDataProcessingController {
	@Autowired
	private ExpDataProcessingService expDataProcessingService;
	
    /**
     * 此处要传递日期来实现对应日期数据的实现，在页面可以过滤出未处理数据的日期然后生成日期按钮
     * @param params dates必须传时间
     * @return
     */
    @RequestMapping("/doSomething")
	public R doSomething(@RequestParam Map<String, Object> params) {
    	expDataProcessingService.doSomething(params);
		return R.ok();
	}
   
}
