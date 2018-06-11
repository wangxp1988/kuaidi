package io.renren.modules.sys.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.service.ExpReceivablesService;

@RestController
@RequestMapping("sys/receivables")
public class ExpReceivablesController {

	@Autowired
	private ExpReceivablesService expReceivablesService;
	/**
	 * 应收账款汇总
	 * @param params
	 * @return
	 */
	@RequestMapping("list")
	@RequiresPermissions("sys:receivables:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = expReceivablesService.queryPage(params);
		return R.ok().put("page", page);
	}
}
