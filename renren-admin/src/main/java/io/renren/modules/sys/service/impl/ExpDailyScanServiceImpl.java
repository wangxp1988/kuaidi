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

import io.renren.modules.sys.dao.ExpDailyScanDao;
import io.renren.modules.sys.entity.ExpDailyScanEntity;
import io.renren.modules.sys.service.ExpDailyScanService;


@Service("expDailyScanService")
public class ExpDailyScanServiceImpl extends ServiceImpl<ExpDailyScanDao, ExpDailyScanEntity> implements ExpDailyScanService {
    @Autowired
	private ExpDailyScanDao expDailyScanDao;
    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExpDailyScanEntity> page = this.selectPage(
                new Query<ExpDailyScanEntity>(params).getPage(),
                new EntityWrapper<ExpDailyScanEntity>().addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
        );

        return new PageUtils(page);
    }

	@Override
	public void saveList(List<ExpDailyScanEntity> tempList) {
		expDailyScanDao.saveList(tempList);
		
	}

}
