package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.ExpTempEntity;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 中转表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-06-08 08:53:49
 */
public interface ExpTempDao extends BaseMapper<ExpTempEntity> {

	List<ExpTempEntity> selectFromRookieByDate(@Param("dates")String dates,@Param("filter")String filter);

	void saveList(List<ExpTempEntity> list);

	List<ExpTempEntity> selectFromBalanceAccount(@Param("filter")String filter,@Param("list")List<Object> list);
	
}
