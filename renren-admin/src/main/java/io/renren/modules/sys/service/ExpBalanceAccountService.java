package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.ExpBalanceAccountEntity;

import java.util.Map;

/**
 * 每日对账表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-25 09:29:17
 */
public interface ExpBalanceAccountService extends IService<ExpBalanceAccountEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

