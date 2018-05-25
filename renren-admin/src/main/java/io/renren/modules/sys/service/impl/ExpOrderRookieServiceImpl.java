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

import io.renren.modules.sys.dao.ExpOrderRookieDao;
import io.renren.modules.sys.entity.ExpOrderRookieEntity;
import io.renren.modules.sys.service.ExpOrderRookieService;


@Service("expOrderRookieService")
public class ExpOrderRookieServiceImpl extends ServiceImpl<ExpOrderRookieDao, ExpOrderRookieEntity> implements ExpOrderRookieService {

	@Autowired
	private ExpOrderRookieDao expOrderRookieDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExpOrderRookieEntity> page = this.selectPage(
                new Query<ExpOrderRookieEntity>(params).getPage(),
                new EntityWrapper<ExpOrderRookieEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public void saveList(List<ExpOrderRookieEntity> tempList) {
		expOrderRookieDao.saveList(tempList);
		
	}

}
