package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.ExpBillPaymentEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 账单收支
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-06-27 22:46:59
 */
public interface ExpBillPaymentDao extends BaseMapper<ExpBillPaymentEntity> {
	
	
	/**
	 * 在账单中统计出账单数据
	 * @param param
	 * @return
	 */
	List<ExpBillPaymentEntity> SelectExpBillPaymentByVoucher(Map<String, Object> param);

	void saveBatch(List<ExpBillPaymentEntity> list);

	int selctCountInTimes(Map<String, Object> params);
	
}
