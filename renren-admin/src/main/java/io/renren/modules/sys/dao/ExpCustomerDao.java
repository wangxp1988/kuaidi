package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.ExpCustomerEntity;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 客户信息
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-27 20:22:52
 */
public interface ExpCustomerDao extends BaseMapper<ExpCustomerEntity> {

	void saveList(List<ExpCustomerEntity> tempList);

	List<ExpCustomerEntity> selectCustomerInRookie(List<Object> listCode);
	
}
