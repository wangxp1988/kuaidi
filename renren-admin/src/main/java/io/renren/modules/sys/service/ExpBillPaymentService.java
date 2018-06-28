package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.ExpBillPaymentEntity;

import java.util.Map;

/**
 * 账单收支
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-06-27 22:46:59
 */
public interface ExpBillPaymentService extends IService<ExpBillPaymentEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

