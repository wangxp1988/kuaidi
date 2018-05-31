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
import io.renren.modules.sys.service.ExpCustomerService;
import io.renren.modules.sys.service.ExpDailyScanService;
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
	private ExpDailyScanService expDailyScanService;// 每日扫描
	@Autowired
	private ExpOrderRookieService expOrderRookieService;// 菜鸟订单
	@Autowired
    private ExpBalanceAccountService expBalanceAccountService;//对账单
    @Autowired
	private ExpCustomerService expCustomerService;//客户
    @Autowired
    private ExpPriceService expPriceService;//价格表
    @Autowired
    private ExpOrdersService  expOrdersService;//中转数据
    /**
     * 此处要传递日期来实现对应日期数据的实现，在页面可以过滤出未处理数据的日期然后生成日期按钮
     * @param params dates必须传时间
     * @return
     */
    @RequestMapping("/doSomething")
	public R doSomething(@RequestParam Map<String, Object> params) {
    	
    	//处理新用户的添加
    	//1、查找菜鸟用户编码
    	List<Object> listRookCustomerCode=expOrderRookieService.selectCustomerCode(params);
    	//2、查找客户中的用户编码
    	List<Object> listCustomerCode=expCustomerService.selectCustomerCode(params);
    	//3、查找客户在没有的菜鸟用户编码
    	 List<Object> listCode= listCompare(listCustomerCode,listRookCustomerCode);
    	//4、查找出对应的用户信息
    	List<ExpCustomerEntity> listCustomer=expCustomerService.selectCustomerInRookie(listCode);
    	//添加到用户表中
    	expCustomerService.saveList(listCustomer);
    	//检查今日扫描中有的运单号 菜鸟中无的，我要到对账单中 查找到相应的运单号及信息
        List<Object> scanWaybillList=expDailyScanService.selectWaybill(params);//只查询运单号
        @SuppressWarnings("unused")
		List<Object> rookieWaybillList=expOrderRookieService.selectWaybill(params);//只查询运单号
        List<Object> list= listCompare(rookieWaybillList,scanWaybillList);//得到菜鸟中没有的运单号
        // 处理菜鸟中没有的订单信息，中转到指定数据库
        params.put("list", list);
        List<ExpOrdersEntity> listOne=expOrdersService.selectNotInRookie(params);
        //处理菜鸟和对账单符合的订单，中转到指定数据库 参数dates
        List<ExpOrdersEntity> listTwo=expOrdersService.selectInRookie(params);
        listOne.addAll(listTwo);//合并结果，批量存入中转数据库
        //expOrdersService.saveOrdersBatch(listOne);//批量保存到数据库
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
