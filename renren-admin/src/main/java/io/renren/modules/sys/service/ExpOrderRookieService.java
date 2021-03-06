package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.ExpOrderRookieEntity;

import java.util.List;
import java.util.Map;

/**
 * 菜鸟订单
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-25 10:53:16
 */
public interface ExpOrderRookieService extends IService<ExpOrderRookieEntity> {

    PageUtils queryPage(Map<String, Object> params);
    /**
     * 批量保存
     * @param tempList
     */
	void saveList(List<ExpOrderRookieEntity> tempList);
	int selectByTime(Map<String, Object> params);
	
	List<Object> selectWaybill(Map<String, Object> params);
	List<Object> selectCustomerCode(Map<String, Object> params);
	List<Object> getDateList(Map<String, Object> params);
	Object getCreateDate(Map<String, Object> params);
	void deleteByDate(Map<String, Object> params);
}

