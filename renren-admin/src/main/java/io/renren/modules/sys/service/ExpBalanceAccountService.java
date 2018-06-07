package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.ExpBalanceAccountEntity;

import java.util.Date;
import java.util.List;
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

	void saveList(List<ExpBalanceAccountEntity> tempList);

	int selectByTime(Map<String, Object> params);

	List<Object> getCustomerName(Map<String, Object> params);
}

