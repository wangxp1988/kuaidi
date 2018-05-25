package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.ExpOrderRookieEntity;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 菜鸟订单
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-25 10:53:16
 */
public interface ExpOrderRookieDao extends BaseMapper<ExpOrderRookieEntity> {

	void saveList(List<ExpOrderRookieEntity> tempList);
	
}
