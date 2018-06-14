package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.ExpVoucherEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

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
	/**
	 * 毛利条数
	 * @param startDates
	 * @param endDates
	 * @param filter
	 * @return
	 */
	int selectCountMy(@Param("startDates")String startDates,@Param("endDates")String endDates,@Param("filter")String filter);
	/**
	 * 毛利明细
	 * @param baseBil
	 * @param currPage
	 * @param limit
	 * @param startDates
	 * @param endDates
	 * @param filter
	 * @return
	 */
	List<ExpVoucherEntity> selectPageMy(@Param("baseBil")BigDecimal baseBil,@Param("currPage")int currPage,@Param("limit")int limit,@Param("startDates")String startDates,@Param("endDates")String endDates,@Param("filter")String filter);
	
	//Map<String, Object> SelectGrossProfitSum(@Param("baseBil")BigDecimal baseBil,@Param("filter")String filter,@Param("customer")String customer,@Param("weight")int weight,@Param("province")String province );
	/**
	 * 毛利汇总
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> SelectGrossProfitSum(Map<String,Object> map );
	/**
	 * 应收款汇总
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectReceivables(Map<String,Object> map);
	/**
	 * 应收款汇总 数量
	 * @param params
	 * @return
	 */
	int selectReceivablesCount(Map<String, Object> params);
	
	/**
	 *  根据用户编码和日期，获取账单   
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectReceivablesByCode(Map<String, Object> params);
	
	
	/**
	 * 根据日期和客户编码获取初期余额
	 * @param params
	 * @return
	 */
	BigDecimal selectInitialBalance(Map<String, Object> params);
	/**
	 * 根据日期和客户编码获取期末余额
	 * @param params
	 * @return
	 */
	BigDecimal selectEndingBalance(Map<String, Object> params);
	
	/**
	 *  根据用户ID集合和日期，获取账单  列表
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectReceivablesByCodes(Map<String, Object> params);
	
	
	/**
	 *  根据用户ID集合和日期，获取账单  数量
	 * @param params
	 * @return
	 */
	int selectReceivablesByCodesCount(Map<String, Object> params);
	BigDecimal selectReceivablesDebtorSum(Map<String, Object> params);
}
