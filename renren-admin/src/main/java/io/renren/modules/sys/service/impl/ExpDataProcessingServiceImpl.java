package io.renren.modules.sys.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import io.renren.common.utils.Constant;
import io.renren.common.utils.R;
import io.renren.modules.sys.dao.ExpCustomerDao;
import io.renren.modules.sys.dao.ExpOrdersDao;
import io.renren.modules.sys.dao.ExpVoucherDao;
import io.renren.modules.sys.entity.ExpCustomerEntity;
import io.renren.modules.sys.entity.ExpMoneyInOutEntity;
import io.renren.modules.sys.entity.ExpOrdersEntity;
import io.renren.modules.sys.entity.ExpTempEntity;
import io.renren.modules.sys.entity.ExpVoucherEntity;
import io.renren.modules.sys.service.ExpBalanceAccountService;
import io.renren.modules.sys.service.ExpBaseService;
import io.renren.modules.sys.service.ExpCustomerService;
import io.renren.modules.sys.service.ExpDailyScanService;
import io.renren.modules.sys.service.ExpDataProcessingService;
import io.renren.modules.sys.service.ExpMoneyInOutService;
import io.renren.modules.sys.service.ExpOrderRookieService;
import io.renren.modules.sys.service.ExpOrdersService;
import io.renren.modules.sys.service.ExpTempService;
import io.renren.modules.sys.service.ExpVoucherService;
import io.renren.modules.sys.shiro.ShiroUtils;

@Service("expDataProcessingService")
public class ExpDataProcessingServiceImpl implements ExpDataProcessingService {

	@Autowired
	private ExpDailyScanService expDailyScanService;// 每日扫描
	@Autowired
	private ExpOrderRookieService expOrderRookieService;// 菜鸟订单
    @Autowired
	private ExpCustomerService expCustomerService;//客户
    @Autowired
    private ExpOrdersService  expOrdersService;//中转数据
    @Autowired
    private ExpVoucherDao expVoucherDao;//凭证DAO
    @Autowired
    private ExpBalanceAccountService expBalanceAccountService;
	@Autowired
	private ExpOrdersDao expOrdersDao;
	@Autowired
	private ExpBaseService expBaseService;
	@Autowired
	private ExpCustomerDao expCustomerDao;
    @Autowired
    private ExpVoucherService expVoucherService;
    @Autowired
    private ExpMoneyInOutService expMoneyInOutService;
    @Autowired
    private ExpTempService expTempService;
    
    private static List<Object> list=new ArrayList<Object>();
	@Override
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor=Exception.class)
	public R doSomething(Map<String, Object> params) {
		Long deptId = ShiroUtils.getUserEntity().getDeptId();//获取登录用的部门ID
		 
		
/******************中转菜鸟**************************************************************/
		if(params.get("num").equals("1")) {
			//从菜鸟中获取当天中转表数据，并批量保存
			try {
				//查询是否已经中转
				int count = expTempService.selectCounts(params);
				if(count<1) {
				List<ExpTempEntity> list=expTempService.selectFromRookieByDate(params);
				this.batchSaveTemp(list);
				}
				return R.ok().put("num", 1).put("msg", "今日菜鸟中转完成");
			} catch (Exception e) {
				e.printStackTrace();
				return R.error().put("num", 1).put("msg", "今日菜鸟中转异常");
			}
			
		}
/******************中转菜鸟完成**************************************************************/
		
/******************中转表中客户和客户表中客户编码做对比********************************************/
		if(params.get("num").equals("2")) {
		//处理新用户的添加
    	//1、查找菜鸟七天内新用户添加完成
    	List<Object> listRookCustomerCode=expTempService.selectCustomerCode(params);
    	//2、查找客户中的用户编码
    	List<Object> listCustomerCode=expCustomerService.selectCustomerCode(params);
    	//3、查找客户在没有的菜鸟用户编码
    	 List<Object> listCode= listCompare(listCustomerCode,listRookCustomerCode);
    	//4、查找出对应的用户信息
    	List<ExpCustomerEntity> listCustomer=expCustomerService.selectCustomerInRookie(listCode);
    	if(null!=listCustomer&&listCustomer.size()>0) {
    		//添加到用户表中
    		expCustomerDao.saveList(listCustomer);
    	}
    	
    	//检查今日扫描中有的运单号 菜鸟中无的，我要到对账单中 查找到相应的运单号及信息
        List<Object> scanWaybillList=expDailyScanService.selectWaybill(params);//扫描表运单号
		List<Object> rookieWaybillList=expTempService.selectWaybill(params);//只查中转7天内询运单号
        list = listCompare(rookieWaybillList,scanWaybillList);//得到中转中没有的
        //-------------------得到菜鸟中没有的运单号---------------------------------
        return R.ok().put("num", 2).put("msg", "今日扫描与中转表对比完成");
		}
/******************中转表中客户和客户表中客户编码做对比，list是中转中没有扫描的单号集合完成*******************/
        params.put("list", list);
/******************通过中转中没有的扫描单号List，来对比对账单中的数据***********************************/
        if(params.get("num").equals("3")) {
        try {
        //菜鸟中没有List的订单要在对账单子对比出来，并获取新用户
        List<Object> listNameNew=expBalanceAccountService.getCustomerName(params);
        List<Object> listNameOld=expCustomerService.getCustomerName(params);
        List<Object> listName= listCompare(listNameOld,listNameNew);
        List<ExpCustomerEntity> newCustomer=new ArrayList<ExpCustomerEntity>();
        if(null!=listName&&listName.size()>0) {
        	listName.forEach(item->{
        		ExpCustomerEntity expCustomerEntity=new ExpCustomerEntity();
        		expCustomerEntity.setName(item.toString());
        		expCustomerEntity.setDeptId(deptId);
        		newCustomer.add(expCustomerEntity);
        	});
    		//添加到用户表中
        	expCustomerDao.saveList(newCustomer);
    	    }
        return R.ok().put("num", 3).put("msg", "对比客户完成");
        	} catch (Exception e) {
        		return R.error().put("num", 3).put("msg", "对比客户异常");
			}
        
        }
/******************通过中转中没有的扫描单号List，来对比对账单中的数据***********************************/
        
/******************查看客户表中是否有空白的用户****************************************************/
        //--------------此处判断用户信息是否完善---------------------------------------------
        if(params.get("num").equals("4")) {
        //此处判断用户信息是否完善SELECT COUNT(id) FROM exp_customer WHERE `code` IS NULL OR price_name IS NULL OR type IS NULL
	        int count=expCustomerService.selectNullCount(params);
	        if(count>0) {
	        	return R.error().put("num", 4).put("msg", "客户信息未完善，请先去客户信息菜单中完善客户信息");
	        }else {
	        	return R.ok().put("num", 4).put("msg", "客户信息已经完善");
	        }
        }
/******************查看客户表中是否有空白的用户完成****************************************************/
        
/******************将对账中的比对出来的信息保存到中转表中****************************************************/
        if(params.get("num").equals("5")) {
        	try {
        		List<ExpTempEntity> listBalanceAccount=expTempService.selectFromBalanceAccount(params);
        		expTempService.saveList(listBalanceAccount);
        		return R.ok().put("num", 5).put("msg", "对账比对后中转成功");
			} catch (Exception e) {
				e.printStackTrace();
				return R.error().put("num", 5).put("msg", "对账比对后中转异常"); 
			}
        	
        }
/******************将对账中的比对出来的信息保存到中转表中完成****************************************************/ 
        
/******************今日扫描和中转表关联数据计算重量处理并保存****************************************************/
        if(params.get("num").equals("6")) {
        	try {
        		//查出对应基数
        		BigDecimal baseWeight=expBaseService.selectBaseWeight(params);
        		params.put("baseWeight", baseWeight);
        		/* //通过list（list保存的是菜鸟中没有，扫描有的运单号），然后扫描和对账关联取出对应数据
        		 List<ExpOrdersEntity> listOne=expOrdersService.selectNotInRookie(params);
     	        //处理菜鸟和对账单符合的订单，中转到指定数据库 参数dates
     	        List<ExpOrdersEntity> listTwo=expOrdersService.selectInRookie(params);
     	        //合并结果，批量存入中转数据库
     	        listOne.addAll(listTwo); */
        		List<ExpOrdersEntity> listOnes =expOrdersService.selectScanAndTemp(params);
     	       expOrdersDao.saveOrdersBatch(listOnes);//批量保存到数据库,并将重量按照规则转化成整数
     	       return R.ok().put("num", 6).put("msg", "数据计算重量处理完成");
			} catch (Exception e) {
				return R.error().put("num",6).put("msg","数据计算重量处理完成");
			}
        }
/******************数据计算重量处理并保存****************************************************/    
        
/******************数据计算重量处理并保存****************************************************/  
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if(params.get("num").equals("7")) {
        	try {
		        //4、利用价格表 计算出运费收入 用户信息及价格等信息都可以获得
        		//查询的是exp_order表
        		//从生成的订单中获取对应的快递费，通过和计算价格表来获得
				List<ExpOrdersEntity> orderList=expOrdersService.selectMoneyList(params);
		        //orderList生成收入凭证
		        List<ExpVoucherEntity> voucherList=new ArrayList<ExpVoucherEntity>();
		        orderList.forEach(item->{
		        	ExpVoucherEntity voucherBorrow=new ExpVoucherEntity();
		        	voucherBorrow.setVoucherRemark(item.getWaybillNumber()+"-"+item.getDestinationProvince()+"-"+item.getCustomerName()+"-"+item.getWeight()+"公斤");//凭证摘要
		        	voucherBorrow.setTwoLevelCoding(Constant.IN_SECOND_CODE_BORROW);
		        	voucherBorrow.setTwoLevelName(Constant.IN_SECOND_NAME_BORROW);
		        	voucherBorrow.setCustomerName(item.getCustomerName());
		        	voucherBorrow.setWaybillNumber(item.getWaybillNumber());
		        	voucherBorrow.setDestinationDot(item.getDestinationProvince());
		        	voucherBorrow.setDebtorMoney(item.getMoney());
		        	voucherBorrow.setDebtorWeight(item.getWeight());
		        	voucherBorrow.setCustomerCode(item.getCustomerCode());
		        	voucherBorrow.setCreateDate(item.getCreateDate());//时间
		        	voucherBorrow.setVoucherCode(sdf.format(item.getCreateDate())+"1");
		        	voucherBorrow.setDeptId(deptId);
		        	voucherList.add(voucherBorrow);
		        	ExpVoucherEntity voucherLoan=new ExpVoucherEntity();
		        	voucherLoan.setVoucherRemark(item.getWaybillNumber()+"-"+item.getDestinationProvince()+"-"+item.getCustomerName()+"-"+item.getWeight()+"公斤");//凭证摘要
		        	voucherLoan.setTwoLevelCoding(Constant.IN_SECOND_CODE_LOAN);
		        	voucherLoan.setTwoLevelName(Constant.IN_SECOND_NAME_LOAN);
		        	voucherLoan.setCustomerName(item.getCustomerName());
		        	voucherLoan.setWaybillNumber(item.getWaybillNumber());
		        	voucherLoan.setDestinationDot(item.getDestinationProvince());
		        	voucherLoan.setLenderMoney(item.getMoney());
		        	voucherLoan.setLenderWeight(item.getOldWeight());
		        	voucherLoan.setCustomerCode(item.getCustomerCode());
		        	voucherLoan.setCreateDate(item.getCreateDate());//时间
		        	voucherLoan.setVoucherCode(sdf.format(item.getCreateDate())+"1");
		        	voucherLoan.setDeptId(deptId);
		        	voucherList.add(voucherLoan);
		        });
		        this.batchSave(voucherList);//批量保存凭证
		        return R.ok().put("num", 7).put("msg", "收入凭证处理完成");
        	} catch (Exception e) {
        		 return R.error().put("num", 7).put("msg", "收入凭证处理异常");
			}
        }
        //老板
        if(params.get("num").equals("8")) {
        	try {
        	List<ExpVoucherEntity> voucherList=new ArrayList<ExpVoucherEntity>();
        	List<ExpMoneyInOutEntity> Nlist=expMoneyInOutService.selectLikeNIn(params);
        	Nlist.forEach(item->{
        		ExpVoucherEntity voucherBorrow=new ExpVoucherEntity();
        		voucherBorrow.setVoucherRemark(item.getWaybillNumber()+"-"+item.getColumnName()+"-"+item.getMoney()+"元");//凭证摘要
	        	voucherBorrow.setTwoLevelCoding(Constant.BOSS_IN_OUT_SECOND_CODE_BORROW);
	        	voucherBorrow.setTwoLevelName(Constant.BOSS_IN_OUT_SECOND_NAME_BORROW);
	        	voucherBorrow.setWaybillNumber(item.getWaybillNumber());
	        	voucherBorrow.setDebtorMoney(item.getMoney());
	        	voucherBorrow.setCreateDate(item.getCreateDate());//时间
	        	voucherBorrow.setVoucherCode(sdf.format(item.getCreateDate()));
	        	voucherBorrow.setDeptId(deptId);
	        	voucherList.add(voucherBorrow);
        		ExpVoucherEntity voucherLoan=new ExpVoucherEntity();
        		voucherLoan.setVoucherRemark(item.getWaybillNumber()+"-"+item.getColumnName()+"-"+item.getMoney()+"元");//凭证摘要
	        	voucherLoan.setTwoLevelCoding(Constant.BOSS_IN_OUT_SECOND_CODE_LOAN);
	        	voucherLoan.setTwoLevelName(Constant.BOSS_IN_OUT_SECOND_NAME_LOAN);
	        	voucherLoan.setWaybillNumber(item.getWaybillNumber());
	        	voucherLoan.setLenderMoney(item.getMoney());
	        	voucherLoan.setCreateDate(item.getCreateDate());//时间
	        	voucherLoan.setVoucherCode(sdf.format(item.getCreateDate())+2);
	        	voucherLoan.setDeptId(deptId);
	        	voucherList.add(voucherLoan);
        	});
        	List<ExpMoneyInOutEntity> outlist=expMoneyInOutService.selectLikeNOut(params);
        	outlist.forEach(item->{
        		ExpVoucherEntity voucherBorrow=new ExpVoucherEntity();
        		voucherBorrow.setVoucherRemark(item.getWaybillNumber()+"-"+item.getColumnName()+"-"+item.getMoney()+"元");//凭证摘要
	        	voucherBorrow.setTwoLevelCoding(Constant.BILL_IN_OUT_SECOND_CODE_BORROW);
	        	voucherBorrow.setTwoLevelName(Constant.BILL_IN_OUT_SECOND_NAME_BORROW);
	        	voucherBorrow.setWaybillNumber(item.getWaybillNumber());
	        	voucherBorrow.setDebtorMoney(item.getMoney());
	        	voucherBorrow.setCreateDate(item.getCreateDate());//时间
	        	voucherBorrow.setVoucherCode(sdf.format(item.getCreateDate()));
	        	voucherBorrow.setDeptId(deptId);
	        	voucherList.add(voucherBorrow);
        		ExpVoucherEntity voucherLoan=new ExpVoucherEntity();
        		voucherLoan.setVoucherRemark(item.getWaybillNumber()+"-"+item.getColumnName()+"-"+item.getMoney()+"元");//凭证摘要
	        	voucherLoan.setTwoLevelCoding(Constant.BILL_IN_OUT_SECOND_CODE_LOAN);
	        	voucherLoan.setTwoLevelName(Constant.BILL_IN_OUT_SECOND_NAME_LOAN);
	        	voucherLoan.setWaybillNumber(item.getWaybillNumber());
	        	voucherLoan.setLenderMoney(item.getMoney());
	        	voucherLoan.setCreateDate(item.getCreateDate());//时间
	        	voucherLoan.setVoucherCode(sdf.format(item.getCreateDate())+2);
	        	voucherLoan.setDeptId(deptId);
	        	voucherList.add(voucherLoan);
        	});
        	this.batchSave(voucherList);//批量保存凭证
	        return R.ok().put("num", 8).put("msg", "收支凭证处理（老板、面单）完成");
        	} catch (Exception e) {
       		 return R.error().put("num", 8).put("msg", "收支凭证处理（老板、面单）完成");
			}
           }
        //收支表中支出凭证
        	if(params.get("num").equals("9")) {
	        	try {
	        		List<ExpOrdersEntity> orderList=expOrdersService.selectOutOrder(params);
	        		List<ExpVoucherEntity> voucherList=new ArrayList<ExpVoucherEntity>();
	        		 orderList.forEach(item->{
	 		        	ExpVoucherEntity voucherBorrow=new ExpVoucherEntity();
	 		        	voucherBorrow.setVoucherRemark(item.getWaybillNumber()+"-"+item.getDestinationProvince()+"-"+item.getCustomerName()+"-"+item.getWeight()+"公斤");//凭证摘要
	 		        	voucherBorrow.setTwoLevelCoding(Constant.OUT_IN_OUT_SECOND_CODE_BORROW);
	 		        	voucherBorrow.setTwoLevelName(Constant.OUT_IN_OUT_SECOND_NAME_BORROW);
	 		        	voucherBorrow.setCustomerName(item.getCustomerName());
	 		        	voucherBorrow.setWaybillNumber(item.getWaybillNumber());
	 		        	voucherBorrow.setDestinationDot(item.getDestinationProvince());
	 		        	voucherBorrow.setDebtorMoney(item.getMoney());
	 		        	voucherBorrow.setDebtorWeight(item.getWeight());
	 		        	voucherBorrow.setCustomerCode(item.getCustomerCode());
	 		        	voucherBorrow.setCreateDate(item.getCreateDate());//时间
	 		        	voucherBorrow.setVoucherCode(sdf.format(item.getCreateDate())+2);
	 		        	voucherBorrow.setDeptId(deptId);
	 		        	voucherList.add(voucherBorrow);
	 		        	ExpVoucherEntity voucherLoan=new ExpVoucherEntity();
	 		        	voucherLoan.setVoucherRemark(item.getWaybillNumber()+"-"+item.getDestinationProvince()+"-"+item.getCustomerName()+"-"+item.getWeight()+"公斤");//凭证摘要
	 		        	voucherLoan.setTwoLevelCoding(Constant.OUT_IN_OUT_SECOND_CODE_LOAN);
	 		        	voucherLoan.setTwoLevelName(Constant.OUT_IN_OUT_SECOND_NAME_LOAN);
	 		        	voucherLoan.setCustomerName(item.getCustomerName());
	 		        	voucherLoan.setWaybillNumber(item.getWaybillNumber());
	 		        	voucherLoan.setDestinationDot(item.getDestinationProvince());
	 		        	voucherLoan.setLenderMoney(item.getMoney());
	 		        	voucherLoan.setLenderWeight(item.getOldWeight());
	 		        	voucherLoan.setCustomerCode(item.getCustomerCode());
	 		        	voucherLoan.setCreateDate(item.getCreateDate());//时间
	 		        	voucherLoan.setVoucherCode(sdf.format(item.getCreateDate())+2);
	 		        	voucherLoan.setDeptId(deptId);
	 		        	voucherList.add(voucherLoan);
	 		        });
	 		        this.batchSave(voucherList);//批量保存凭证
	 		        return R.ok().put("num", 9).put("msg", "收支表——支出凭证处理完成");
	        	}catch (Exception e) {
	        		e.printStackTrace();
	        		return R.error().put("num", 9).put("msg", "收支表——支出凭证处理失败");
				}
        	}
        	
        	//收支表中收入
        	if(params.get("num").equals("10")) {
        		try {
        			List<ExpOrdersEntity> orderList=expOrdersService.selectInOrder(params);
        			List<ExpVoucherEntity> voucherList=new ArrayList<ExpVoucherEntity>();
        			if(null!=orderList&&orderList.size()>0) {
   	        		 orderList.forEach(item->{
   	 		        	ExpVoucherEntity voucherBorrow=new ExpVoucherEntity();
   	 		        	voucherBorrow.setVoucherRemark(item.getWaybillNumber()+"-"+item.getDestinationProvince()+"-"+item.getCustomerName()+"-"+item.getWeight()+"公斤");//凭证摘要
   	 		        	voucherBorrow.setTwoLevelCoding(Constant.IN_IN_OUT_SECOND_CODE_BORROW);
   	 		        	voucherBorrow.setTwoLevelName(Constant.IN_IN_OUT_SECOND_NAME_BORROW+"-"+item.getDes());
   	 		        	voucherBorrow.setCustomerName(item.getCustomerName());
   	 		        	voucherBorrow.setWaybillNumber(item.getWaybillNumber());
   	 		        	voucherBorrow.setDestinationDot(item.getDestinationProvince());
   	 		        	voucherBorrow.setDebtorMoney(item.getMoney());
   	 		        	voucherBorrow.setDebtorWeight(item.getWeight());
   	 		        	voucherBorrow.setCustomerCode(item.getCustomerCode());
   	 		        	voucherBorrow.setCreateDate(item.getCreateDate());//时间
   	 		        	voucherBorrow.setVoucherCode(sdf.format(item.getCreateDate())+2);
   	 		        	voucherBorrow.setDeptId(deptId);
   	 		        	voucherList.add(voucherBorrow);
   	 		        	ExpVoucherEntity voucherLoan=new ExpVoucherEntity();
   	 		        	voucherLoan.setVoucherRemark(item.getWaybillNumber()+"-"+item.getDestinationProvince()+"-"+item.getCustomerName()+"-"+item.getWeight()+"公斤");//凭证摘要
   	 		        	voucherLoan.setTwoLevelCoding(Constant.IN_IN_OUT_SECOND_CODE_LOAN);
   	 		        	voucherLoan.setTwoLevelName(Constant.IN_IN_OUT_SECOND_NAME_LOAN+"-"+item.getDes());
   	 		        	voucherLoan.setCustomerName(item.getCustomerName());
   	 		        	voucherLoan.setWaybillNumber(item.getWaybillNumber());
   	 		        	voucherLoan.setDestinationDot(item.getDestinationProvince());
   	 		        	voucherLoan.setLenderMoney(item.getMoney());
   	 		        	voucherLoan.setLenderWeight(item.getOldWeight());
   	 		        	voucherLoan.setCustomerCode(item.getCustomerCode());
   	 		        	voucherLoan.setCreateDate(item.getCreateDate());//时间
   	 		        	voucherLoan.setVoucherCode(sdf.format(item.getCreateDate())+2);
   	 		        	voucherLoan.setDeptId(deptId);
   	 		        	voucherList.add(voucherLoan);
   	 		        });
        			}
        			//查找收入但是不包含N
        			List<ExpMoneyInOutEntity> listin=expMoneyInOutService.selectInNotIsN(params);
        			listin.forEach(item->{
                		ExpVoucherEntity voucherBorrow=new ExpVoucherEntity();
                		voucherBorrow.setVoucherRemark(item.getWaybillNumber()+"-"+item.getColumnName()+"-"+item.getMoney()+"元");//凭证摘要
        	        	voucherBorrow.setTwoLevelCoding(Constant.PAI_IN_OUT_SECOND_CODE_BORROW);
        	        	voucherBorrow.setTwoLevelName(Constant.PAI_IN_OUT_SECOND_NAME_BORROW);
        	        	voucherBorrow.setWaybillNumber(item.getWaybillNumber());
        	        	voucherBorrow.setDebtorMoney(item.getMoney());
        	        	voucherBorrow.setCreateDate(item.getCreateDate());//时间
        	        	voucherBorrow.setVoucherCode(sdf.format(item.getCreateDate())+2);
        	        	voucherBorrow.setDeptId(deptId);
        	        	voucherList.add(voucherBorrow);
                		ExpVoucherEntity voucherLoan=new ExpVoucherEntity();
                		voucherLoan.setVoucherRemark(item.getWaybillNumber()+"-"+item.getColumnName()+"-"+item.getMoney()+"元");//凭证摘要
        	        	voucherLoan.setTwoLevelCoding(Constant.PAI_IN_OUT_SECOND_CODE_LOAN);
        	        	voucherLoan.setTwoLevelName(Constant.PAI_IN_OUT_SECOND_NAME_LOAN);
        	        	voucherLoan.setWaybillNumber(item.getWaybillNumber());
        	        	voucherLoan.setLenderMoney(item.getMoney());
        	        	voucherLoan.setCreateDate(item.getCreateDate());//时间
        	        	voucherLoan.setVoucherCode(sdf.format(item.getCreateDate())+2);
        	        	voucherLoan.setDeptId(deptId);
        	        	voucherList.add(voucherLoan);
                	});
        			 this.batchSave(voucherList);//批量保存凭证
        			 return R.ok().put("num", 10).put("msg", "收支表——收入凭证处理完成");
				} catch (Exception e) {
					e.printStackTrace();
					 return R.error().put("num",10).put("msg", "收支表——收入凭证处理失败");
				}
        	}
        	//收款凭证
        	if(params.get("num").equals("11")) {
        		 try {
        			 List<ExpOrdersEntity> orderList=expOrdersService.selectGeneralIn(params);
 	        		List<ExpVoucherEntity> voucherList=new ArrayList<ExpVoucherEntity>();
 	        		 orderList.forEach(item->{
 	 		        	ExpVoucherEntity voucherBorrow=new ExpVoucherEntity();
 	 		        	if(StringUtils.isNoneBlank(item.getWaybillNumber())) {
 	 		        		voucherBorrow.setVoucherRemark(item.getWaybillNumber()+"-"+item.getDestinationProvince()+"-"+item.getCustomerName()+"-"+item.getWeight()+"公斤");//凭证摘要
 	 		        	}else {
 	 		        		voucherBorrow.setVoucherRemark(Constant.DAY_IN_OUT_SECOND_NAME_BORROW+"-"+item.getDes());//凭证摘要
 	 		        	}
 	 		        	
 	 		        	voucherBorrow.setTwoLevelCoding(Constant.DAY_IN_OUT_SECOND_CODE_BORROW);
 	        		    voucherBorrow.setTwoLevelName(Constant.DAY_IN_OUT_SECOND_NAME_BORROW+"-"+item.getDes());
 	 		        	voucherBorrow.setCustomerName(item.getCustomerName());
 	 		        	voucherBorrow.setWaybillNumber(item.getWaybillNumber());
 	 		        	voucherBorrow.setDestinationDot(item.getDestinationProvince());
 	 		        	voucherBorrow.setDebtorMoney(item.getMoney());
 	 		        	voucherBorrow.setDebtorWeight(item.getWeight());
 	 		        	voucherBorrow.setCustomerCode(item.getCustomerCode());
 	 		        	voucherBorrow.setCreateDate(item.getCreateDate());//时间
 	 		        	voucherBorrow.setVoucherCode(sdf.format(item.getCreateDate())+3);
 	 		        	voucherBorrow.setDeptId(deptId);
 	 		        	voucherList.add(voucherBorrow);
 	 		        	ExpVoucherEntity voucherLoan=new ExpVoucherEntity();
 	 		        	if(StringUtils.isNoneBlank(item.getWaybillNumber())) {
 	 		        		voucherLoan.setVoucherRemark(item.getWaybillNumber()+"-"+item.getDestinationProvince()+"-"+item.getCustomerName()+"-"+item.getWeight()+"公斤");//凭证摘要
 	 		        	}else {
 	 		        		voucherLoan.setVoucherRemark(Constant.DAY_IN_OUT_SECOND_NAME_LOAN+"-"+item.getDes());//凭证摘要
 	 		        	}
 	 		        	voucherLoan.setTwoLevelCoding(Constant.DAY_IN_OUT_SECOND_CODE_LOAN);
 	 		        	voucherLoan.setTwoLevelName(Constant.DAY_IN_OUT_SECOND_NAME_LOAN);
 	 		        	voucherLoan.setCustomerName(item.getCustomerName());
 	 		        	voucherLoan.setWaybillNumber(item.getWaybillNumber());
 	 		        	voucherLoan.setDestinationDot(item.getDestinationProvince());
 	 		        	voucherLoan.setLenderMoney(item.getMoney());
 	 		        	voucherLoan.setLenderWeight(item.getOldWeight());
 	 		        	voucherLoan.setCustomerCode(item.getCustomerCode());
 	 		        	voucherLoan.setCreateDate(item.getCreateDate());//时间
 	 		        	voucherLoan.setVoucherCode(sdf.format(item.getCreateDate())+3);
 	 		        	voucherLoan.setDeptId(deptId);
 	 		        	voucherList.add(voucherLoan);
 	 		        });
 	 		        this.batchSave(voucherList);//批量保存凭证
 	 		      return R.ok().put("num",11).put("msg", "日常收支——收款凭证处理完成");
				} catch (Exception e) {
					 e.printStackTrace();
					 return R.error().put("num",11).put("msg", "日常收支——收款凭证处理失败");
				}
        	}
        
        return R.error();
	}
	 private List<Object> listCompare(List<Object> rookieWaybillList, List<Object> scanWaybillList) {
	    	long startTime=System.currentTimeMillis(); 
	    	if(null==scanWaybillList&&scanWaybillList.size()<1) {
	    		return null;
	    	}else if(null==rookieWaybillList&&rookieWaybillList.size()<1){
	    		return scanWaybillList;
	    	}
	        Map<Object,Integer> map = new HashMap<Object,Integer>(rookieWaybillList.size());
	        Set<Object> differentList = new HashSet<Object>();
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
			List<Object> list=new ArrayList<>();
			list.addAll(differentList);
	        return list;
	    }
	 
	 /**
	  * 批量保存凭证
	  * @param list
	  */
	 public void batchSave(List<ExpVoucherEntity> list) {
		 if (null != list) {
	    		long startTime=System.currentTimeMillis(); 
				int g=list.size()/100;
				int j=0;
				j=g;
				if(list.size()%100>0){
					j++;
				}
				System.out.println("j=="+j);
				if(j==g){
					for (int i=0;i<g;i++) {
						List<ExpVoucherEntity> tempList=new ArrayList<ExpVoucherEntity>();
						tempList.addAll(list.subList(i*100, (i+1)*100));
						expVoucherDao.saveList(tempList);
					}
				}else if(j>g){
					for (int i=0;i<j;i++) {
						List<ExpVoucherEntity> tempList=new ArrayList<ExpVoucherEntity>();
						if(i<j-1){
							tempList.addAll(list.subList(i*100, (i+1)*100));
						}else if(i==j-1){
							tempList.addAll(list.subList(i*100, (i*100+list.size()%100)));
						}
						expVoucherDao.saveList(tempList);
					}
				}
				long endTime=System.currentTimeMillis(); //获取结束时间
				System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
	    	} 
	 }
	 
	 /**
	  * 批量中转保存凭证
	  * @param list
	  */
	 public void batchSaveTemp(List<ExpTempEntity> list) {
		 if (null != list) {
				int g=list.size()/100;
				int j=0;
				j=g;
				if(list.size()%100>0){
					j++;
				}
				System.out.println("j=="+j);
				if(j==g){
					for (int i=0;i<g;i++) {
						List<ExpTempEntity> tempList=new ArrayList<ExpTempEntity>();
						tempList.addAll(list.subList(i*100, (i+1)*100));
						expTempService.saveList(tempList);
					}
				}else if(j>g){
					for (int i=0;i<j;i++) {
						List<ExpTempEntity> tempList=new ArrayList<ExpTempEntity>();
						if(i<j-1){
							tempList.addAll(list.subList(i*100, (i+1)*100));
						}else if(i==j-1){
							tempList.addAll(list.subList(i*100, (i*100+list.size()%100)));
						}
						expTempService.saveList(tempList);
					}
				}
	    	} 
	 }
	 
    /**
     * 查询菜鸟日期，并去重，然后在查询凭证表中日期做对比
     */
	public List<Object> getDateList(Map<String, Object> params) {
		
		List<Object> dateListR = expOrderRookieService.getDateList(params);
		List<Object> dateListV =expVoucherService.getDateList(params);
		return listCompare(dateListV, dateListR);
		
	}
}
