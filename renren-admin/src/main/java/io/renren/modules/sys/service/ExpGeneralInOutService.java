package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.ExpGeneralInOutEntity;

import java.util.List;
import java.util.Map;

/**
 * 日常收支
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-25 09:29:17
 */
public interface ExpGeneralInOutService extends IService<ExpGeneralInOutEntity> {

    PageUtils queryPage(Map<String, Object> params);

	void saveList(List<ExpGeneralInOutEntity> tempList);

	int selectByTime(Map<String, Object> params);
}

