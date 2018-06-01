package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.ExpOrdersEntity;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 中转数据表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-31 09:24:05
 */
public interface ExpOrdersDao extends BaseMapper<ExpOrdersEntity> {

	List<ExpOrdersEntity> selectNotInRookie(@Param("list")List list, @Param("filter")String filter);

	List<ExpOrdersEntity> selectInRookie(@Param("dates")String dates,@Param("filter")String filter);

	void saveOrdersBatch(List<ExpOrdersEntity> list);

	List<ExpOrdersEntity> selectMoneyList(@Param("dates")String dates, @Param("filter")String filter);
	
}
