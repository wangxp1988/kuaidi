package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.renren.common.annotation.DataFilter;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.ExpCustomerTypeDao;
import io.renren.modules.sys.entity.ExpCustomerTypeEntity;
import io.renren.modules.sys.service.ExpCustomerTypeService;


@Service("expCustomerTypeService")
public class ExpCustomerTypeServiceImpl extends ServiceImpl<ExpCustomerTypeDao, ExpCustomerTypeEntity> implements ExpCustomerTypeService {

    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExpCustomerTypeEntity> page = this.selectPage(
                new Query<ExpCustomerTypeEntity>(params).getPage(),
                new EntityWrapper<ExpCustomerTypeEntity>().addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
        );

        return new PageUtils(page);
    }

	@Override
	 @DataFilter(subDept = true, user = false)
	public List<ExpCustomerTypeEntity> listAll(Map<String, Object> params) {
		return  this.selectList(new EntityWrapper<ExpCustomerTypeEntity>().addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER)));
	}

}
