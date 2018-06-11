package io.renren.modules.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.renren.common.annotation.DataFilter;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.ExpVoucherDao;
import io.renren.modules.sys.entity.ExpVoucherEntity;
import io.renren.modules.sys.service.ExpReceivablesService;

@Service("expReceivablesService")
public class ExpReceivablesServiceImpl extends ServiceImpl<ExpVoucherDao, ExpVoucherEntity> implements ExpReceivablesService  {

	@Autowired
	private ExpVoucherDao expVoucherDao;
	@Override
	@DataFilter(subDept = true, user = false)
	public PageUtils queryPage(Map<String, Object> params) {
			 int count=this.expVoucherDao.selectReceivablesCount(params);
			 Query query=new Query<Map<String, Object>>(params);
			 params.put("currPage", (query.getCurrPage()-1)*query.getLimit());
			 params.put("limit", query.getLimit());
			 List<Map<String, Object>> list=this.expVoucherDao.selectReceivables(params);
		     return  new PageUtils(list, count, query.getLimit(), query.getCurrPage());
	}

}
