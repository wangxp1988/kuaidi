package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.ExpRechargeDao;
import io.renren.modules.sys.entity.ExpRechargeEntity;
import io.renren.modules.sys.service.ExpRechargeService;


@Service("expRechargeService")
public class ExpRechargeServiceImpl extends ServiceImpl<ExpRechargeDao, ExpRechargeEntity> implements ExpRechargeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExpRechargeEntity> page = this.selectPage(
                new Query<ExpRechargeEntity>(params).getPage(),
                new EntityWrapper<ExpRechargeEntity>()
        );

        return new PageUtils(page);
    }

}
