package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.ExpVoucherEntity;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 凭证管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-06-03 12:07:07
 */
public interface ExpVoucherDao extends BaseMapper<ExpVoucherEntity> {

	void saveList(List<ExpVoucherEntity> tempList);
	
}
