package io.renren.modules.sys.service.impl;

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
        Page<ExpCustomerEntity> page = this.selectPage(
                new Query<ExpCustomerEntity>(params).getPage(),
                new EntityWrapper<ExpCustomerEntity>().addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER)).orderDesc(columns)
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
	public List<ExpCustomerEntity> selectCustomerInRookie(List<Object> listCode) {
		return expCustomerDao.selectCustomerInRookie(listCode);
	}

}
