package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.ExpVoucherEntity;

import java.util.List;
import java.util.Map;

/**
 * 凭证管理
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-06-03 12:07:07
 */
public interface ExpVoucherService extends IService<ExpVoucherEntity> {

    PageUtils queryPage(Map<String, Object> params);

	List<Object> getDateList(Map<String, Object> params);
}

