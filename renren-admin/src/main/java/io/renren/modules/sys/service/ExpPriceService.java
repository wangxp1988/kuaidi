package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.ExpPriceEntity;

import java.util.List;
import java.util.Map;

/**
 * 价格表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-25 09:29:17
 */
public interface ExpPriceService extends IService<ExpPriceEntity> {

    PageUtils queryPage(Map<String, Object> params);

	void saveList(List<ExpPriceEntity> tempList);

	List<ExpPriceEntity> listAllName(Map<String, Object> params);

	List<ExpPriceEntity> selectAllDept(Map<String, Object> params);

	void deleteAll(Map<String, Object> params);
}

