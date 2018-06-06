package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.renren.common.annotation.DataFilter;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.ExpBaseDao;
import io.renren.modules.sys.entity.ExpBaseEntity;
import io.renren.modules.sys.service.ExpBaseService;


@Service("expBaseService")
public class ExpBaseServiceImpl extends ServiceImpl<ExpBaseDao, ExpBaseEntity> implements ExpBaseService {

    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExpBaseEntity> page = this.selectPage(
                new Query<ExpBaseEntity>(params).getPage(),
                new EntityWrapper<ExpBaseEntity>()
                .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
        );

        return new PageUtils(page);
    }

	@Override
	@DataFilter(subDept = true, user = false)
	public BigDecimal selectBaseWeight(Map<String, Object> params) {
		return (BigDecimal) this.selectObj(new EntityWrapper<ExpBaseEntity>()
				.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
				.setSqlSelect("base_weight")
				.last("limit 0,1") 
				);
	}

}
