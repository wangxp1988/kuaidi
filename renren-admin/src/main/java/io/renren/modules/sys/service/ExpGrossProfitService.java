package io.renren.modules.sys.service;

import java.math.BigDecimal;
import java.util.Map;


import com.baomidou.mybatisplus.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.ExpVoucherEntity;

public interface ExpGrossProfitService  extends IService<ExpVoucherEntity> {

	PageUtils queryPage(Map<String, Object> params);
	/**
	 * 按条件汇总
	 * @param baseBil
	 * @param filte
	 * @param customer
	 * @param weight
	 * @param province
	 * @return
	 */
	Map<String, Object> SelectGrossProfitSum(Map<String, Object> params);

}
