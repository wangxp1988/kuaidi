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
import io.renren.modules.sys.entity.ExpDailyScanEntity;
import io.renren.modules.sys.service.ExpBalanceAccountService;
import io.renren.modules.sys.service.ExpCustomerService;
import io.renren.modules.sys.service.ExpDailyScanService;
import io.renren.modules.sys.service.ExpOrderRookieService;
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
	private ExpDailyScanService expDailyScanService;// 每日扫描
	@Autowired
	private ExpOrderRookieService expOrderRookieService;// 菜鸟订单
	@Autowired
    private ExpBalanceAccountService expBalanceAccountService;//对账单
    @Autowired
	private ExpCustomerService expCustomerService;//客户
    @Autowired
    private ExpPriceService expPriceService;//价格表
    /**
     * 此处要传递日期来实现对应日期数据的实现，在页面可以过滤出未处理数据的日期然后生成日期按钮
     * @param params
     * @return
     */
    @RequestMapping("/doSomething")
	public R doSomething(@RequestParam Map<String, Object> params) {
    	
    	//检查今日扫描中有的运单号 菜鸟中无的，我要到对账单中 查找到相应的运单号及信息
        List<Object> scanWaybillList=expDailyScanService.selectWaybill(params);//只查询运单号
        @SuppressWarnings("unused")
		List<Object> rookieWaybillList=expOrderRookieService.selectWaybill(params);//只查询运单号
        List<Object> list= listCompare(rookieWaybillList,scanWaybillList);//得到菜鸟中没有的运单号
        //expBalanceAccountService.selectAccount();
        //SELECT s.waybill_number,s.weight,s.sender,a.send_province,s.branch,a.recipient_province from exp_daily_scan s INNER JOIN exp_balance_account a ON s.waybill_number=a.waybill_number  where s.waybill_number IN ('90001414501906')
        //expBalanceAccountService.select
		return R.ok();
	}
    
    
    private List<Object> listCompare(List<Object> rookieWaybillList, List<Object> scanWaybillList) {
    	long startTime=System.currentTimeMillis(); 
        Map<Object,Integer> map = new HashMap<Object,Integer>(rookieWaybillList.size());
        List<Object> differentList = new ArrayList<Object>();
        for(Object resource : rookieWaybillList){
                map.put(resource,1);
        }
        for(Object rookieWaybill : scanWaybillList){
            if(map.get(rookieWaybill)==null){
                differentList.add(rookieWaybill);
            }
        }
        long endTime=System.currentTimeMillis(); //获取结束时间
		System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
        return differentList;
    }
}
