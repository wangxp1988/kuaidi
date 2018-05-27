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

import io.renren.modules.sys.dao.ExpBalanceAccountDao;
import io.renren.modules.sys.entity.ExpBalanceAccountEntity;
import io.renren.modules.sys.service.ExpBalanceAccountService;


@Service("expBalanceAccountService")
public class ExpBalanceAccountServiceImpl extends ServiceImpl<ExpBalanceAccountDao, ExpBalanceAccountEntity> implements ExpBalanceAccountService {

	@Autowired
	private ExpBalanceAccountDao expBalanceAccountEntity;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExpBalanceAccountEntity> page = this.selectPage(
                new Query<ExpBalanceAccountEntity>(params).getPage(),
                new EntityWrapper<ExpBalanceAccountEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public void saveList(List<ExpBalanceAccountEntity> tempList) {
		expBalanceAccountEntity.saveList(tempList);
		
	}

}
