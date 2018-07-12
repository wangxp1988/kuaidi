package io.renren.modules.sys.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.R;
import io.renren.modules.sys.service.ExpBalanceAccountService;
import io.renren.modules.sys.service.ExpDailyScanService;
import io.renren.modules.sys.service.ExpGeneralInOutService;
import io.renren.modules.sys.service.ExpMoneyInOutService;
import io.renren.modules.sys.service.ExpOrderRookieService;
import io.renren.modules.sys.service.ExpOrdersService;
import io.renren.modules.sys.service.ExpTempService;
import io.renren.modules.sys.service.ExpVoucherService;

@RestController
@RequestMapping("sys/alldata")
public class AllDataManagerController {
    @Autowired
	private ExpBalanceAccountService expBalanceAccountService;
    @Autowired
    private ExpDailyScanService expDailyScanService;
    @Autowired
    private ExpGeneralInOutService expGeneralInOutService;
    @Autowired
    private ExpMoneyInOutService expMoneyInOutService;
    @Autowired
    private ExpOrderRookieService expOrderRookieService;
    @Autowired
    private ExpOrdersService expOrdersService;
    @Autowired
    private ExpTempService expTempService;
    @Autowired
    private ExpVoucherService  expVoucherService;
	
	@RequestMapping("/delByDate")
	public R delByDate(@RequestParam Map<String, Object> params) {
		try {
			expBalanceAccountService.deleteByDate(params);
			expDailyScanService.deleteByDate(params);
			expGeneralInOutService.deleteByDate(params);
			expMoneyInOutService.deleteByDate(params);
			expOrderRookieService.deleteByDate(params);
			expOrdersService.deleteByDate(params);
			expTempService.deleteByDate(params);
			expVoucherService.deleteByDate(params);
		} catch (Exception e) {
			 R.error("删除失败，请重新删除");
		}
		
		return R.ok();
		
	}
}
