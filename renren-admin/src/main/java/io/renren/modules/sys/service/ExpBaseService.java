package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.ExpBaseEntity;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 基础数据
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-06-06 09:34:11
 */
public interface ExpBaseService extends IService<ExpBaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

	BigDecimal selectBaseWeight(Map<String, Object> params);
	public BigDecimal selectBaseBill(Map<String, Object> params);
}

