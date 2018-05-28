package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.ExpMoneyInOutEntity;

import java.util.List;
import java.util.Map;

/**
 * 收支
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-28 15:39:17
 */
public interface ExpMoneyInOutService extends IService<ExpMoneyInOutEntity> {

    PageUtils queryPage(Map<String, Object> params);

	void saveList(List<ExpMoneyInOutEntity> tempList);
}

