package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.ExpMoneyInOutEntity;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 收支
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-28 15:39:17
 */
public interface ExpMoneyInOutDao extends BaseMapper<ExpMoneyInOutEntity> {

	void saveList(List<ExpMoneyInOutEntity> tempList);
	
}
