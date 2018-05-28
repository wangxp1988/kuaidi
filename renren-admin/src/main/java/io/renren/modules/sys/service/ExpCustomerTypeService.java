package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.ExpCustomerTypeEntity;

import java.util.List;
import java.util.Map;

/**
 * 客户类型
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-28 11:36:06
 */
public interface ExpCustomerTypeService extends IService<ExpCustomerTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);

	List<ExpCustomerTypeEntity> listAll(Map<String, Object> params);
}

