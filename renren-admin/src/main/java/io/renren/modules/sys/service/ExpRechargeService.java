package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.ExpRechargeEntity;

import java.util.Map;

/**
 * 充值记录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-06-18 17:13:27
 */
public interface ExpRechargeService extends IService<ExpRechargeEntity> {

    PageUtils queryPage(Map<String, Object> params);

	void saveR(ExpRechargeEntity expRecharge, String availableDate);
}

