package io.renren.modules.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.SysAreaDao;
import io.renren.modules.sys.entity.SysAreaEntity;
import io.renren.modules.sys.service.SysAreaService;


@Service("sysAreaService")
public class SysAreaServiceImpl extends ServiceImpl<SysAreaDao, SysAreaEntity> implements SysAreaService {

	@Autowired
	private SysAreaDao sysAreaDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SysAreaEntity> page = this.selectPage(
                new Query<SysAreaEntity>(params).getPage(),
                new EntityWrapper<SysAreaEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public List<SysAreaEntity> selectProvinceList(Long parentId) {
		return sysAreaDao.selectList( new EntityWrapper<SysAreaEntity>().eq("parent_id", parentId));
	}

}
