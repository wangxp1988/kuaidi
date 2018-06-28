package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.ExpBillPaymentDao;
import io.renren.modules.sys.entity.ExpBillPaymentEntity;
import io.renren.modules.sys.service.ExpBillPaymentService;


@Service("expBillPaymentService")
public class ExpBillPaymentServiceImpl extends ServiceImpl<ExpBillPaymentDao, ExpBillPaymentEntity> implements ExpBillPaymentService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExpBillPaymentEntity> page = this.selectPage(
                new Query<ExpBillPaymentEntity>(params).getPage(),
                new EntityWrapper<ExpBillPaymentEntity>()
        );

        return new PageUtils(page);
    }

}
