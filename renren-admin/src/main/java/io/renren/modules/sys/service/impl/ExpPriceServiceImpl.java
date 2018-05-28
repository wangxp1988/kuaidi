package io.renren.modules.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
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

import io.renren.modules.sys.dao.ExpPriceDao;
import io.renren.modules.sys.entity.ExpPriceEntity;
import io.renren.modules.sys.service.ExpPriceService;


@Service("expPriceService")
public class ExpPriceServiceImpl extends ServiceImpl<ExpPriceDao, ExpPriceEntity> implements ExpPriceService {

	@Autowired
	private ExpPriceDao expPriceDao;
    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExpPriceEntity> page = this.selectPage(
                new Query<ExpPriceEntity>(params).getPage(),
                new EntityWrapper<ExpPriceEntity>().addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
        );

        return new PageUtils(page);
    }

	@Override
	public void saveList(List<ExpPriceEntity> tempList) {
		expPriceDao.saveList(tempList);
		
	}

	@Override
	 @DataFilter(subDept = true, user = false)
	public List<ExpPriceEntity> listAllName(Map<String, Object> params) {
		return  this.selectList(new EntityWrapper<ExpPriceEntity>()
				.groupBy("price_name")
				.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER)));
		  
	}

}
