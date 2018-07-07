package io.renren.modules.sys.service.impl;

import org.apache.commons.lang.StringUtils;
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

import io.renren.modules.sys.dao.ExpBillPaymentDao;
import io.renren.modules.sys.entity.ExpBillPaymentEntity;
import io.renren.modules.sys.service.ExpBillPaymentService;


@Service("expBillPaymentService")
public class ExpBillPaymentServiceImpl extends ServiceImpl<ExpBillPaymentDao, ExpBillPaymentEntity> implements ExpBillPaymentService {

	@Autowired
	private ExpBillPaymentDao expBillPaymentDao;
    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
    	System.out.println(params.get("customer")!=null&&StringUtils.isNotBlank((String)params.get("customer")));
        Page<ExpBillPaymentEntity> page = this.selectPage(
                new Query<ExpBillPaymentEntity>(params).getPage(),
                new EntityWrapper<ExpBillPaymentEntity>()
                .setSqlSelect("id","billing_period","customer_type","customer_code","customer_name","receivable","IFNULL(paid,0) as paid","(receivable-IFNULL(paid,0)) as unpaid")
                .eq(params.get("billingPeriod")!=null&&StringUtils.isNotBlank((String)params.get("billingPeriod")), "billing_period", (String)params.get("billingPeriod"))
                .and(params.get("customer")!=null&&StringUtils.isNotBlank((String)params.get("customer")), "(customer_code='"+(String)params.get("customer")+"' OR customer_name LIKE '%"+(String)params.get("customer")+"%')")
                .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
                .orderBy("receivable", false)
        );
        return new PageUtils(page);
    }

    @DataFilter(subDept = true, user = false,tableAlias="v")
	public void SelectExpBillPaymentByVoucher(Map<String, Object> params) {
    	if(null!=params.get(Constant.SQL_FILTER)) {
    		params.put("sql_filter_one", params.get(Constant.SQL_FILTER).toString().replaceAll("v.dept_id", "c.dept_id"));
    	}
    	//先要判断这个时间段内是不是已经做了汇总，如果已经做了，那么就要跳过
    	//SELECT COUNT(*) FROM exp_bill_payment v where  ('2018-06-18'<=v.start_date<='2018-06-19' OR '2018-06-20'<=v.end_date<='2018-06-21')
    	int count=expBillPaymentDao.selctCountInTimes(params);
    	if(count==0) {
    		List<ExpBillPaymentEntity> list=expBillPaymentDao.SelectExpBillPaymentByVoucher(params);
        	expBillPaymentDao.saveBatch(list);
    	}
	}
    @DataFilter(subDept = true, user = false)
    public List<Object> SelectExpBillPeriod(Map<String, Object> params){
    	return this.expBillPaymentDao.selectObjs(
    			new EntityWrapper<ExpBillPaymentEntity>()
    			.setSqlSelect("billing_period")
    			.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
    			.groupBy("billing_period")
    			);
    }
    

}
