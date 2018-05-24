package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysTestEntity;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-22 15:17:59
 */
public interface SysTestService extends IService<SysTestEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

