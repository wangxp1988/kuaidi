package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.ExpDailyScanEntity;

import java.util.List;
import java.util.Map;

/**
 * 每日扫描
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-25 09:29:17
 */
public interface ExpDailyScanService extends IService<ExpDailyScanEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
	void saveList(List<ExpDailyScanEntity> tempList);

	int selectByTime(Map<String, Object> params);

	List<Object> selectWaybill(Map<String, Object> params);

	List<Object> getCustomerName(Map<String, Object> params);

	void deleteByDate(Map<String, Object> params);
}

