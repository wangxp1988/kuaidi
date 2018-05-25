package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.ExpPriceDao;
import io.renren.modules.sys.entity.ExpPriceEntity;
import io.renren.modules.sys.service.ExpPriceService;


@Service("expPriceService")
public class ExpPriceServiceImpl extends ServiceImpl<ExpPriceDao, ExpPriceEntity> implements ExpPriceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExpPriceEntity> page = this.selectPage(
                new Query<ExpPriceEntity>(params).getPage(),
                new EntityWrapper<ExpPriceEntity>()
        );

        return new PageUtils(page);
    }

}
