package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.ExpOrdersEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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

	List<ExpOrdersEntity> selectNotInRookie(@Param("list")List list, @Param("filter")String filter,@Param("baseWeight")BigDecimal baseWeight);

	List<ExpOrdersEntity> selectInRookie(@Param("dates")String dates,@Param("filter")String filter,@Param("baseWeight")BigDecimal baseWeight);

	void saveOrdersBatch(List<ExpOrdersEntity> list);

	List<ExpOrdersEntity> selectMoneyList(Map<String, Object> params);

	List<ExpOrdersEntity> selectOutOrder(@Param("dates")String dates, @Param("filter")String filter);

	List<ExpOrdersEntity> selectInOrder(@Param("dates")String dates, @Param("filter")String filter);

	List<ExpOrdersEntity> selectGeneralIn(@Param("dates")String dates, @Param("filter")String filter);

	List<ExpOrdersEntity> selectScanAndTemp(Map<String, Object> params);
	
}
