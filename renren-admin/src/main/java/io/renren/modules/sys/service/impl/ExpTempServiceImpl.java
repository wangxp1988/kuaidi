package io.renren.modules.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.renren.common.annotation.DataFilter;
import io.renren.common.utils.Constant;
import io.renren.modules.sys.dao.ExpTempDao;
import io.renren.modules.sys.entity.ExpBalanceAccountEntity;
import io.renren.modules.sys.entity.ExpTempEntity;
import io.renren.modules.sys.service.ExpTempService;


@Service("expTempService")
public class ExpTempServiceImpl extends ServiceImpl<ExpTempDao, ExpTempEntity> implements ExpTempService {

	@Autowired
	private  ExpTempDao expTempDao;
	/**
	 * 通过时间从菜鸟中获取中转表数据
	 */
	@Override
	@DataFilter(subDept = true, user = false)
	public List<ExpTempEntity> selectFromRookieByDate(Map<String, Object> params) {
		if(null!=params.get("dates")&&!"".equals(params.get("dates").toString())) {
			return expTempDao.selectFromRookieByDate(params.get("dates").toString(),(String)params.get(Constant.SQL_FILTER));
	}
	return null;
	}
	@Override
	public void saveList(List<ExpTempEntity> list) {
		expTempDao.saveList(list);
	}
	/**
	 * 获取七天内的用户信息
	 */
	@Override
	@DataFilter(subDept = true, user = false)
	public List<Object> selectCustomerCode(Map<String, Object> params) {
		 List<Object> list=expTempDao.selectObjs(
					new EntityWrapper<ExpTempEntity>().setSqlSelect("customer_code")
					.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
					.and("TO_DAYS(str_to_date('"+params.get("dates")+"', '%Y-%m-%d')) - TO_DAYS(create_date) BETWEEN 0 AND 7")
					 );
			 Set<Object> set=new HashSet<Object>();
			 set.addAll(list);
			 list.clear();
			 list.addAll(set);
			return list;
	}
	
	/**
	 * 获取七天内的运单号
	 */
	@Override
	@DataFilter(subDept = true, user = false)
	public List<Object> selectWaybill(Map<String, Object> params) {
		return  expTempDao.selectObjs(
				new EntityWrapper<ExpTempEntity>().setSqlSelect("waybill_number")
				.and("TO_DAYS(str_to_date('"+params.get("dates")+"', '%Y-%m-%d')) - TO_DAYS(create_date) BETWEEN 0 AND 7")
				.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
				);
	
	}
	/**
	 * 获取对账单中菜鸟没哟的
	 */
	@Override
	@DataFilter(subDept = true, user = false,tableAlias="a")
	public List<ExpTempEntity> selectFromBalanceAccount(Map<String, Object> params) {
			return expTempDao.selectFromBalanceAccount(params.get(Constant.SQL_FILTER).toString().replaceAll("a.dept_id", "c.dept_id"),(String)params.get(Constant.SQL_FILTER),(List<Object>)params.get("list"));
	}
	@Override
	@DataFilter(subDept = true, user = false)
	public int selectCounts(Map<String, Object> params) {
		return expTempDao.selectCount(new EntityWrapper<ExpTempEntity>()
				.eq("DATE_FORMAT(create_date,'%Y-%m-%d')", params.get("dates"))
				.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
				);
	}

}
