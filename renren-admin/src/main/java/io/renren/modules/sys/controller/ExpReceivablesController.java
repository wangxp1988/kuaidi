package io.renren.modules.sys.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.service.ExpReceivablesService;

@RestController
@RequestMapping("sys/receivables")
public class ExpReceivablesController {

	@Value("${filepath.excleSavePath}")
	private String diskDirPath;
	
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
	/**
	 * 参数 开始时间，结束时间 ，客户类型  ，结果类型（只查借款，全查）
	 * @param params
	 * @return
	 */
	@RequestMapping("billlist")
	@RequiresPermissions("sys:receivables:billlist")
	public R receivableslist(@RequestParam Map<String, Object> params) {
		PageUtils page = expReceivablesService.queryReceivablesPage(params);
		return R.ok().put("page", page);
	}
	
	/**
	 * 参数 开始时间，结束时间 ，客户类型  ，结果类型（只查借款，全查）
	 * @param params
	 * @return
	 */
	@RequestMapping("export")
	@RequiresPermissions("sys:receivables:export")
	public void receivablesExport(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> params) {
		 
		expReceivablesService.receivablesExport(response,params,diskDirPath);
		
	}
}
