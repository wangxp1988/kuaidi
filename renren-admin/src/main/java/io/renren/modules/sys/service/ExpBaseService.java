package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.ExpBaseEntity;

import java.util.Map;

/**
 * 基础数据
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-31 09:25:48
 */
public interface ExpBaseService extends IService<ExpBaseEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

