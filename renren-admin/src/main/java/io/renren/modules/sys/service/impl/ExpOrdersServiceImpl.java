package io.renren.modules.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.renren.common.annotation.DataFilter;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.ExpOrdersDao;
import io.renren.modules.sys.entity.ExpOrdersEntity;
import io.renren.modules.sys.service.ExpOrdersService;


@Service("expOrdersService")
public class ExpOrdersServiceImpl extends ServiceImpl<ExpOrdersDao, ExpOrdersEntity> implements ExpOrdersService {
	@Autowired
	private ExpOrdersDao expOrdersDao;

    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExpOrdersEntity> page = this.selectPage(
                new Query<ExpOrdersEntity>(params).getPage(),
                new EntityWrapper<ExpOrdersEntity>().addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
        );

        return new PageUtils(page);
    }

	@Override
	@DataFilter(subDept = true, user = false,tableAlias="s")
	public List<ExpOrdersEntity> selectNotInRookie(Map<String, Object> params) {
		List<Object> list =(List) params.get("list");
		if(null!=list) {
				return  expOrdersDao.selectNotInRookie(list,(String)params.get(Constant.SQL_FILTER),new BigDecimal(params.get("baseWeight").toString()));
		}
	  return null;
	}

	@Override
	@DataFilter(subDept = true, user = false,tableAlias="r")
	public List<ExpOrdersEntity> selectInRookie(Map<String, Object> params) {
		if(null!=params.get("dates")&&!"".equals(params.get("dates").toString())) {
				return expOrdersDao.selectInRookie(params.get("dates").toString(),(String)params.get(Constant.SQL_FILTER),new BigDecimal(params.get("baseWeight").toString()));
		}
		return null;
	}

	@Override
	@Transactional
	public void saveOrdersBatch(List<ExpOrdersEntity> list) {
		expOrdersDao.saveOrdersBatch(list);
	}

	@Override
	@DataFilter(subDept = true, user = false)
	public List<ExpOrdersEntity> selectMoneyList(Map<String, Object> params) {
		
		if(null!=params.get("sql_filter")) {
			params.put("sql_filter_one", params.get("sql_filter").toString().replace("dept_id", "o.dept_id"));
			params.put("sql_filter_two", params.get("sql_filter").toString().replace("dept_id", "p.dept_id"));
		}
		if(null!=params.get("dates")&&!"".equals(params.get("dates").toString())) {
			return expOrdersDao.selectMoneyList(params);
	   }
	   return null;
	}

	@Override
	@DataFilter(subDept = true, user = false,tableAlias="o")
	public List<ExpOrdersEntity> selectOutOrder(Map<String, Object> params) {
		if(null!=params.get("dates")&&!"".equals(params.get("dates").toString())) {
			return expOrdersDao.selectOutOrder(params.get("dates").toString(),(String)params.get(Constant.SQL_FILTER));
	   }
		return null;
	}
	@DataFilter(subDept = true, user = false,tableAlias="o")
		public List<ExpOrdersEntity> selectInOrder(Map<String, Object> params) {
			if(null!=params.get("dates")&&!"".equals(params.get("dates").toString())) {
				return expOrdersDao.selectInOrder(params.get("dates").toString(),(String)params.get(Constant.SQL_FILTER));
			}
			return null;
	}
	
	@Override
	@DataFilter(subDept = true, user = false,tableAlias="o")
	public List<ExpOrdersEntity> selectGeneralIn(Map<String, Object> params) {
		if(null!=params.get("dates")&&!"".equals(params.get("dates").toString())) {
			return expOrdersDao.selectGeneralIn(params.get("dates").toString(),(String)params.get(Constant.SQL_FILTER));
		}
		return null;
	}
   /**
    * 今日扫描和中转表关联
    */
	@Override
	@DataFilter(subDept = true, user = false,tableAlias="s")
	public List<ExpOrdersEntity> selectScanAndTemp(Map<String, Object> params) {
		if(null!=params.get("dates")&&!"".equals(params.get("dates").toString())) {
			params.put("sql_filter_one", params.get(Constant.SQL_FILTER).toString().replaceAll("s.dept_id", "t.dept_id"));
			//return expOrdersDao.selectScanAndTemp(params.get("dates").toString(),(String)params.get(Constant.SQL_FILTER),new BigDecimal(params.get("baseWeight").toString()));
			 return expOrdersDao.selectScanAndTemp(params);
	     }
		return null;
	}


}
