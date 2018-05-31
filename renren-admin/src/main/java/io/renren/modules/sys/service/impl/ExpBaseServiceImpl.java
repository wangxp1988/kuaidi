package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.ExpBaseDao;
import io.renren.modules.sys.entity.ExpBaseEntity;
import io.renren.modules.sys.service.ExpBaseService;


@Service("expBaseService")
public class ExpBaseServiceImpl extends ServiceImpl<ExpBaseDao, ExpBaseEntity> implements ExpBaseService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExpBaseEntity> page = this.selectPage(
                new Query<ExpBaseEntity>(params).getPage(),
                new EntityWrapper<ExpBaseEntity>()
        );

        return new PageUtils(page);
    }

}
