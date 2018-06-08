package io.renren.modules.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.renren.common.annotation.DataFilter;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.ExpBalanceAccountDao;
import io.renren.modules.sys.entity.ExpBalanceAccountEntity;
import io.renren.modules.sys.entity.ExpDailyScanEntity;
import io.renren.modules.sys.service.ExpBalanceAccountService;


@Service("expBalanceAccountService")
public class ExpBalanceAccountServiceImpl extends ServiceImpl<ExpBalanceAccountDao, ExpBalanceAccountEntity> implements ExpBalanceAccountService {

	@Autowired
	private ExpBalanceAccountDao expBalanceAccountDao;
    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExpBalanceAccountEntity> page = this.selectPage(
                new Query<ExpBalanceAccountEntity>(params).getPage(),
                new EntityWrapper<ExpBalanceAccountEntity>().addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
        );

        return new PageUtils(page);
    }

	@Override
	public void saveList(List<ExpBalanceAccountEntity> tempList) {
		expBalanceAccountDao.saveList(tempList);
		
	}

	@Override
	@DataFilter(subDept = true, user = false)
	public int selectByTime(Map<String, Object> params) {
		int count=expBalanceAccountDao.selectCount(
				new EntityWrapper<ExpBalanceAccountEntity>()
				.eq(params.get("sendTime")!=null, "send_time", params.get("sendTime"))
				.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
				);
		return count;
	}

	@Override
	@DataFilter(subDept = true, user = false)
	public List<Object> getCustomerName(Map<String, Object> params) {
		List<Object> list=(List<Object>)params.get("list");
		if(null!=list&&list.size()>0) {
			return expBalanceAccountDao.selectObjs(
					new EntityWrapper<ExpBalanceAccountEntity>().setSqlSelect("sender")
					.eq("DATE_FORMAT(send_time,'%Y-%m-%d')", params.get("dates"))
					.in("waybill_number", list)
					.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
					);
		}
		return  null;
	}

}
