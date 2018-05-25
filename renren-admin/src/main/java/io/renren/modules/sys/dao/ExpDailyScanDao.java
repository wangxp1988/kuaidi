package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.ExpDailyScanEntity;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 每日扫描
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-25 09:29:17
 */
public interface ExpDailyScanDao extends BaseMapper<ExpDailyScanEntity> {

	void saveList(List<ExpDailyScanEntity> tempList);
	
}
