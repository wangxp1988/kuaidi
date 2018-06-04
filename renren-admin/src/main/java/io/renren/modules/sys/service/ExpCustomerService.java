package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.ExpCustomerEntity;

import java.util.List;
import java.util.Map;

/**
 * 客户信息
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-27 20:22:52
 */
public interface ExpCustomerService extends IService<ExpCustomerEntity> {

    PageUtils queryPage(Map<String, Object> params);

	void saveList(List<ExpCustomerEntity> tempList);

	List<ExpCustomerEntity> listAllCustomer(Map<String, Object> params);

	List<Object> selectCustomerCode(Map<String, Object> params);

	List<ExpCustomerEntity> selectCustomerInRookie(List<Object> listCode);

	List<Object> getCustomerName(Map<String, Object> params);

	int selectNullCount(Map<String, Object> params);
}

