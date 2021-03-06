package io.renren.modules.sys.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

import io.renren.modules.sys.dao.ExpVoucherDao;
import io.renren.modules.sys.entity.ExpTempEntity;
import io.renren.modules.sys.entity.ExpVoucherEntity;
import io.renren.modules.sys.service.ExpVoucherService;


@Service("expVoucherService")
public class ExpVoucherServiceImpl extends ServiceImpl<ExpVoucherDao, ExpVoucherEntity> implements ExpVoucherService {
	@Autowired
    private ExpVoucherDao expVoucherDao;
    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
    	String voucherCode = (String)params.get("voucherCode");
    	String customerCode = (String)params.get("customerCode");
        Page<ExpVoucherEntity> page = this.selectPage(
                new Query<ExpVoucherEntity>(params).getPage(),
                new EntityWrapper<ExpVoucherEntity>()
                .eq(StringUtils.isNotBlank(voucherCode), "voucher_code", voucherCode)
                .eq(StringUtils.isNotBlank(customerCode), "customer_code", customerCode)
                .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
        );
        return new PageUtils(page);
    }

	@Override
	@DataFilter(subDept = true, user = false)
	public List<Object> getDateList(Map<String, Object> params) {
		List<Object> list = expVoucherDao.selectObjs(new EntityWrapper<ExpVoucherEntity>()
				.setSqlSelect("DATE_FORMAT(create_date,'%Y-%m-%d')")
				.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
				.groupBy("DATE_FORMAT(create_date,'%Y-%m-%d')")
				);
		return list;
	}

	@Override
	@DataFilter(subDept = true, user = false)
	public List<ExpVoucherEntity> selectAllDept(Map<String, Object> params) {
		List<ExpVoucherEntity> list =expVoucherDao.selectList(new EntityWrapper<ExpVoucherEntity>()
						.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
						.and("DATE_FORMAT(create_date,'%Y-%m-%d')='"+params.get("dates")+"'")
						);
		return list;
	}

	@Override
	@DataFilter(subDept = true, user = false)
	public void deleteByDate(Map<String, Object> params) {
		this.delete(new EntityWrapper<ExpVoucherEntity>()
				.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
				.eq("DATE_FORMAT(create_date, '%Y-%m-%d')", params.get("dates")));
	}

}
