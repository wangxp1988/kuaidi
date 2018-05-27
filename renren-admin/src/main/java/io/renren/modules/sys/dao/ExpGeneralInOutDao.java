package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.ExpGeneralInOutEntity;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 日常收支
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-25 09:29:17
 */
public interface ExpGeneralInOutDao extends BaseMapper<ExpGeneralInOutEntity> {

	void saveList(List<ExpGeneralInOutEntity> tempList);

	
}
