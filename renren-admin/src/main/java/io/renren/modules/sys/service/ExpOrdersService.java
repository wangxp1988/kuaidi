package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.ExpOrdersEntity;

import java.util.List;
import java.util.Map;

/**
 * 中转数据表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-31 09:24:05
 */
public interface ExpOrdersService extends IService<ExpOrdersEntity> {

    PageUtils queryPage(Map<String, Object> params);

	List<ExpOrdersEntity> selectNotInRookie(Map<String, Object> params);

	List<ExpOrdersEntity> selectInRookie(Map<String, Object> params);

	void saveOrdersBatch(List<ExpOrdersEntity> listOne);

	List<ExpOrdersEntity> selectMoneyList(Map<String, Object> params);

	List<ExpOrdersEntity> selectOutOrder(Map<String, Object> params);
	List<ExpOrdersEntity> selectInOrder(Map<String, Object> params);
}

