package io.renren.modules.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.renren.common.annotation.DataFilter;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.ExpOrderRookieDao;
import io.renren.modules.sys.entity.ExpDailyScanEntity;
import io.renren.modules.sys.entity.ExpMoneyInOutEntity;
import io.renren.modules.sys.entity.ExpOrderRookieEntity;
import io.renren.modules.sys.service.ExpOrderRookieService;


@Service("expOrderRookieService")
public class ExpOrderRookieServiceImpl extends ServiceImpl<ExpOrderRookieDao, ExpOrderRookieEntity> implements ExpOrderRookieService {

	@Autowired
	private ExpOrderRookieDao expOrderRookieDao;
    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExpOrderRookieEntity> page = this.selectPage(
                new Query<ExpOrderRookieEntity>(params).getPage(),
                new EntityWrapper<ExpOrderRookieEntity>().addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
        );

        return new PageUtils(page);
    }

	@Override
	public void saveList(List<ExpOrderRookieEntity> tempList) {
		expOrderRookieDao.saveList(tempList);
		
	}

	@Override
	@DataFilter(subDept = true, user = false)
	public int selectByTime(Map<String, Object> params) {
		int count=expOrderRookieDao.selectCount(
				new EntityWrapper<ExpOrderRookieEntity>()
				.eq(params.get("createDate")!=null, "create_date", params.get("createDate"))
				.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
				);
		return count;
	}

	@Override
	@DataFilter(subDept = true, user = false)
	public List<Object> selectWaybill(Map<String, Object> params) {
		return  expOrderRookieDao.selectObjs(
				new EntityWrapper<ExpOrderRookieEntity>().setSqlSelect("waybill_number")
				 //.eq("DATE_FORMAT(create_date,'%Y-%m-%d')", params.get("dates"))
				.and("TO_DAYS(str_to_date('"+params.get("dates")+"', '%Y-%m-%d')) - TO_DAYS(create_date) BETWEEN 0 AND 7")
				.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
				);
	
	}
	/**
	 * 查询菜鸟订单中的用户编码
	 */
	@Override
	@DataFilter(subDept = true, user = false)
	public List<Object> selectCustomerCode(Map<String, Object> params) {
		// TODO Auto-generated method stub
		 List<Object> list=expOrderRookieDao.selectObjs(
				new EntityWrapper<ExpOrderRookieEntity>().setSqlSelect("customer_code")
				//.eq("DATE_FORMAT(create_date,'%Y-%m-%d')", params.get("dates"))
				.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
				.and("TO_DAYS(str_to_date('"+params.get("dates")+"', '%Y-%m-%d')) - TO_DAYS(create_date) BETWEEN 0 AND 7")
				 );
		 Set<Object> set=new HashSet<Object>();
		 set.addAll(list);
		 list.clear();
		 list.addAll(set);
		return list;
	}

	@Override
	@DataFilter(subDept = true, user = false)
	public List<Object> getDateList(Map<String, Object> params) {
		List<Object> list = expOrderRookieDao.selectObjs(new EntityWrapper<ExpOrderRookieEntity>()
				.setSqlSelect("DATE_FORMAT(create_date,'%Y-%m-%d')")
				.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
				);
		 Set<Object> set=new HashSet<Object>();
		 set.addAll(list);
		 list.clear();
		 list.addAll(set);
		return list;
	}
	
	/**
	 * 获取菜鸟首次的时间
	 * 
	 * SELECT DATE_FORMAT(create_date, '%Y-%m-%d') AS start_date FROM exp_order_rookie WHERE dept_id=13  ORDER BY  create_date ASC LIMIT 0,1 
	 */
	@Override
	@DataFilter(subDept = true, user = false)
	public Object getCreateDate(Map<String, Object> params) {
		return this.selectObj(
				new EntityWrapper<ExpOrderRookieEntity>()
				.setSqlSelect("DATE_FORMAT(create_date, '%Y-%m-%d')")
				.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
				.orderBy("create_date", true)
				.last("LIMIT 0,1")
				);
	}
}
