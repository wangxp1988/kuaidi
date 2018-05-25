package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.ExpBalanceAccountDao;
import io.renren.modules.sys.entity.ExpBalanceAccountEntity;
import io.renren.modules.sys.service.ExpBalanceAccountService;


@Service("expBalanceAccountService")
public class ExpBalanceAccountServiceImpl extends ServiceImpl<ExpBalanceAccountDao, ExpBalanceAccountEntity> implements ExpBalanceAccountService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExpBalanceAccountEntity> page = this.selectPage(
                new Query<ExpBalanceAccountEntity>(params).getPage(),
                new EntityWrapper<ExpBalanceAccountEntity>()
        );

        return new PageUtils(page);
    }

}
