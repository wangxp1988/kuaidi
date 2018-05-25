package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.ExpGeneralInOutDao;
import io.renren.modules.sys.entity.ExpGeneralInOutEntity;
import io.renren.modules.sys.service.ExpGeneralInOutService;


@Service("expGeneralInOutService")
public class ExpGeneralInOutServiceImpl extends ServiceImpl<ExpGeneralInOutDao, ExpGeneralInOutEntity> implements ExpGeneralInOutService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExpGeneralInOutEntity> page = this.selectPage(
                new Query<ExpGeneralInOutEntity>(params).getPage(),
                new EntityWrapper<ExpGeneralInOutEntity>()
        );

        return new PageUtils(page);
    }

}
