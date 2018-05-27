package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.ExpPriceEntity;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 价格表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-25 09:29:17
 */
public interface ExpPriceDao extends BaseMapper<ExpPriceEntity> {

	void saveList(List<ExpPriceEntity> tempList);
	
}
