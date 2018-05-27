package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.ExpBalanceAccountEntity;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 每日对账表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-25 09:29:17
 */
public interface ExpBalanceAccountDao extends BaseMapper<ExpBalanceAccountEntity> {

	void saveList(List<ExpBalanceAccountEntity> tempList);
	
}
