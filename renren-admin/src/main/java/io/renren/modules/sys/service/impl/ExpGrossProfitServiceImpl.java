package io.renren.modules.sys.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.renren.common.annotation.DataFilter;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.ExpVoucherDao;
import io.renren.modules.sys.entity.ExpVoucherEntity;
import io.renren.modules.sys.service.ExpBaseService;
import io.renren.modules.sys.service.ExpGrossProfitService;

@Service("expProssProfitService")
public class ExpGrossProfitServiceImpl extends ServiceImpl<ExpVoucherDao, ExpVoucherEntity>   implements ExpGrossProfitService {

	@Autowired
	private ExpVoucherDao expVoucherDao;
	@Autowired 
	private ExpBaseService  expBaseService;
	 @Override
	 @DataFilter(subDept = true, user = false,tableAlias="v")
	public PageUtils queryPage(Map<String, Object> params) {
		 BigDecimal baseBil= expBaseService.selectBaseBill(params);
		 String startDates="";
		String endDates="";
		String fillter="";
		 if(null!=params.get("start_dates")) {
			 startDates=params.get("start_dates").toString();
		 }
		 if(null!=params.get("end_dates")) {
			 endDates=params.get("end_dates").toString();
		 }
		 if(null!=params.get(Constant.SQL_FILTER)) {
			 fillter=params.get(Constant.SQL_FILTER).toString();
		 }
		 int count=this.expVoucherDao.selectCountMy(startDates,endDates,(String)params.get(Constant.SQL_FILTER));
		 Query query=new Query<ExpVoucherEntity>(params);
		 List<ExpVoucherEntity> list=this.expVoucherDao.selectPageMy(baseBil,(query.getCurrPage()-1)*query.getLimit(),query.getLimit(),startDates,endDates,fillter);
	     return  new PageUtils(list, count, query.getLimit(), query.getCurrPage());
	}
	 @DataFilter(subDept = true, user = false,tableAlias="v")
	public List<Map<String, Object>> SelectGrossProfitSum(Map<String, Object> params) {
		 BigDecimal baseBil= expBaseService.selectBaseBill(params);
		 params.put("baseBil", baseBil);
		 return this.expVoucherDao.SelectGrossProfitSum(params);
		 
	}
	 
	 

}
