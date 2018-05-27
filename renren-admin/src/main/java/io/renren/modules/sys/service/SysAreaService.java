package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysAreaEntity;

import java.util.List;
import java.util.Map;

/**
 * 行政区划表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-26 21:56:55
 */
public interface SysAreaService extends IService<SysAreaEntity> {

    PageUtils queryPage(Map<String, Object> params);

	List<SysAreaEntity> selectProvinceList(Long parentId);
}

