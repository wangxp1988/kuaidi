package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.ExpCustomerDao;
import io.renren.modules.sys.entity.ExpCustomerEntity;
import io.renren.modules.sys.service.ExpCustomerService;


@Service("expCustomerService")
public class ExpCustomerServiceImpl extends ServiceImpl<ExpCustomerDao, ExpCustomerEntity> implements ExpCustomerService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExpCustomerEntity> page = this.selectPage(
                new Query<ExpCustomerEntity>(params).getPage(),
                new EntityWrapper<ExpCustomerEntity>()
        );

        return new PageUtils(page);
    }

}
