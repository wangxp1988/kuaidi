package io.renren.modules.sys.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.service.ExpGrossProfitService;

/**
 * 毛利查询
 * 
 * @author dunn
 *
 */
@RestController
@RequestMapping("sys/grossprofit")
public class ExpGrossProfitController {
	@Autowired
	private ExpGrossProfitService grossProfitService;
   /**
    * c查询毛利明细
    * @param params
    * @return
    */
	@RequestMapping("list")
	@RequiresPermissions("sys:grossprofit:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = grossProfitService.queryPage(params);
		return R.ok().put("page", page);
	}
	
	@RequestMapping("sum")
	@RequiresPermissions("sys:grossprofit:list")
	public R listsum(@RequestParam Map<String, Object> params) {
		Map<String,Object> o = grossProfitService.SelectGrossProfitSum(params);
		return R.ok().put("map", o);
	}
}
