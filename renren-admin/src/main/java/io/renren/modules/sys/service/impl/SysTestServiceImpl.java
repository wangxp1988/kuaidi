package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.renren.common.annotation.DataFilter;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.SysTestDao;
import io.renren.modules.sys.entity.SysTestEntity;
import io.renren.modules.sys.service.SysTestService;


@Service("sysTestService")
public class SysTestServiceImpl extends ServiceImpl<SysTestDao, SysTestEntity> implements SysTestService {

    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SysTestEntity> page = this.selectPage(
                new Query<SysTestEntity>(params).getPage(),
                new EntityWrapper<SysTestEntity>().addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
        );

        return new PageUtils(page);
    }

}
