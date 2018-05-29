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

import io.renren.modules.sys.dao.ExpGeneralInOutDao;
import io.renren.modules.sys.entity.ExpGeneralInOutEntity;
import io.renren.modules.sys.service.ExpGeneralInOutService;


@Service("expGeneralInOutService")
public class ExpGeneralInOutServiceImpl extends ServiceImpl<ExpGeneralInOutDao, ExpGeneralInOutEntity> implements ExpGeneralInOutService {
    @Autowired
	private ExpGeneralInOutDao expGeneralInOutDao;
    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExpGeneralInOutEntity> page = this.selectPage(
                new Query<ExpGeneralInOutEntity>(params).getPage(),
                new EntityWrapper<ExpGeneralInOutEntity>().addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
        );

        return new PageUtils(page);
    }

	@Override
	public void saveList(List<ExpGeneralInOutEntity> tempList) {
		expGeneralInOutDao.saveList(tempList);
		
	}

	@Override
	 @DataFilter(subDept = true, user = false)
	public int selectByTime(Map<String, Object> params) {
		int count=expGeneralInOutDao.selectCount(
				new EntityWrapper<ExpGeneralInOutEntity>()
				.eq(params.get("createTime")!=null, "create_time", params.get("createTime"))
				.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
				);
		return count;
	}

}
