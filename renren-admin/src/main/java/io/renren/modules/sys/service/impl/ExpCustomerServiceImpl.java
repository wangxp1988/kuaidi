package io.renren.modules.sys.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

import io.renren.modules.sys.dao.ExpCustomerDao;
import io.renren.modules.sys.entity.ExpCustomerEntity;
import io.renren.modules.sys.entity.ExpOrderRookieEntity;
import io.renren.modules.sys.entity.ExpPriceEntity;
import io.renren.modules.sys.service.ExpCustomerService;


@Service("expCustomerService")
public class ExpCustomerServiceImpl extends ServiceImpl<ExpCustomerDao, ExpCustomerEntity> implements ExpCustomerService {
	
	@Autowired
	private ExpCustomerDao expCustomerDao;

    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
    	List<String> columns=new ArrayList<String>();
    	columns.add("id");
    	String name = (String)params.get("name");
    	String code = (String)params.get("code");
    	String type = (String)params.get("type");
    	System.out.println(params.get("name")!=null);
        Page<ExpCustomerEntity> page = this.selectPage(
                new Query<ExpCustomerEntity>(params).getPage(),
                new EntityWrapper<ExpCustomerEntity>().addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
                .like(StringUtils.isNotBlank(name),"name", (String)params.get("name"))
                .eq(StringUtils.isNotBlank(type), "type", (String)params.get("type"))
                .eq(StringUtils.isNotBlank(code), "code", (String)params.get("code"))
                .orderDesc(columns)
        );

        return new PageUtils(page);
    }

	@Override
	public void saveList(List<ExpCustomerEntity> tempList) {
		// TODO Auto-generated method stub
		expCustomerDao.saveList(tempList);
	}

	@Override
	 @DataFilter(subDept = true, user = false)
	public List<ExpCustomerEntity> listAllCustomer(Map<String, Object> params) {
		return this.selectList(new EntityWrapper<ExpCustomerEntity>()
				.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER)));
	}

	@Override
	@DataFilter(subDept = true, user = false)
	public List<Object> selectCustomerCode(Map<String, Object> params) {
				 List<Object> list=expCustomerDao.selectObjs(
						new EntityWrapper<ExpCustomerEntity>().setSqlSelect("code")
						.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
						);
				 Set<Object> set=new HashSet<Object>();
				 set.addAll(list);
				 list.clear();
				 list.addAll(set);
				return list;
	}
	/**
	 * 通过菜鸟表中获取用户信息
	 */
	@Override
	@DataFilter(subDept = true, user = false)
	public List<ExpCustomerEntity> selectCustomerInRookie(Map<String, Object> params) {
		return expCustomerDao.selectCustomerInRookie(params);
	}

	@Override
	@DataFilter(subDept = true, user = false)
	public List<Object> getCustomerName(Map<String, Object> params) {
		List<Object> list=expCustomerDao.selectObjs(
				new EntityWrapper<ExpCustomerEntity>().setSqlSelect("name")
				.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
				);
		 Set<Object> set=new HashSet<Object>();
		 set.addAll(list);
		 list.clear();
		 list.addAll(set);
		return list;
	}
/**
 * SELECT COUNT(id) FROM exp_customer WHERE `code` IS NULL OR price_name IS NULL OR type IS NULL
 */
	@Override
	@DataFilter(subDept = true, user = false)
	public int selectNullCount(Map<String, Object> params) {
		int count=expCustomerDao.selectCount(new EntityWrapper<ExpCustomerEntity>()
				.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
				.andNew("`code` IS NULL")
				.or("`price_name` IS NULL").or("`type` IS NULL")
				);
		return count;
	}

@Override
@DataFilter(subDept = true, user = false)
public List<ExpCustomerEntity> selectAllDept(Map<String, Object> params) {
	List<String> columns=new ArrayList<String>();
	columns.add("id");
	return this.selectList(new EntityWrapper<ExpCustomerEntity>()
			.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
			.orderDesc(columns)
			);
}

@Override
@DataFilter(subDept = true, user = false)
public List<ExpCustomerEntity> selectCustomerByType(Map<String, Object> params) {
	return  this.selectList(new EntityWrapper<ExpCustomerEntity>()
			.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
			.eq(params.get("type") != null, "type", params.get("type"))
			  );
}

@Override
@DataFilter(subDept = true, user = false)
public ExpCustomerEntity selectCustomerByCode(Map<String, Object> params) {
	 
	return this.selectOne(new EntityWrapper<ExpCustomerEntity>()
			.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
			.eq(params.get("customerCode") != null, "code", params.get("customerCode")));
}

}
