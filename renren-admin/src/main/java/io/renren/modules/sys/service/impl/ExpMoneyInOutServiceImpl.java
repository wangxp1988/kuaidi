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

import io.renren.modules.sys.dao.ExpMoneyInOutDao;
import io.renren.modules.sys.entity.ExpGeneralInOutEntity;
import io.renren.modules.sys.entity.ExpMoneyInOutEntity;
import io.renren.modules.sys.service.ExpMoneyInOutService;


@Service("expMoneyInOutService")
public class ExpMoneyInOutServiceImpl extends ServiceImpl<ExpMoneyInOutDao, ExpMoneyInOutEntity> implements ExpMoneyInOutService {
    @Autowired
	private ExpMoneyInOutDao expMoneyInOutDao;
    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExpMoneyInOutEntity> page = this.selectPage(
                new Query<ExpMoneyInOutEntity>(params).getPage(),
                new EntityWrapper<ExpMoneyInOutEntity>().addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
        );

        return new PageUtils(page);
    }

	@Override
	public void saveList(List<ExpMoneyInOutEntity> tempList) {
		 
		expMoneyInOutDao.saveList(tempList);
	}

	@Override
	@DataFilter(subDept = true, user = false)
	public int selectByTime(Map<String, Object> params) {
		int count=expMoneyInOutDao.selectCount(
				new EntityWrapper<ExpMoneyInOutEntity>()
				.eq(params.get("createDate")!=null, "create_date", params.get("createDate"))
				.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
				);
		return count;
	}

}
