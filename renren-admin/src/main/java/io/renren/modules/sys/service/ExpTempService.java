package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.modules.sys.entity.ExpTempEntity;

import java.util.List;
import java.util.Map;

/**
 * 中转表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-06-08 08:53:49
 */
public interface ExpTempService extends IService<ExpTempEntity> {
	List<ExpTempEntity> selectFromRookieByDate(Map<String, Object> params);

	void saveList(List<ExpTempEntity> list);

	List<Object> selectCustomerCode(Map<String, Object> params);

	List<Object> selectWaybill(Map<String, Object> params);

	List<ExpTempEntity> selectFromBalanceAccount(Map<String, Object> params);

	int selectCounts(Map<String, Object> params);

	void deleteByDate(Map<String, Object> params);
}

