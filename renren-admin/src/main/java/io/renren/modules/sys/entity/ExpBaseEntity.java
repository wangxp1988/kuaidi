package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 基础数据
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-06-06 09:34:11
 */
@TableName("exp_base")
public class ExpBaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Long id;
	/**
	 * 重量固定基数
	 */
	private BigDecimal baseWeight;
	/**
	 * 面单成本
	 */
	private BigDecimal baseBill;

	private Long deptId;
	/**
	 * 设置：ID
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：ID
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：重量固定基数
	 */
	public void setBaseWeight(BigDecimal baseWeight) {
		this.baseWeight = baseWeight;
	}
	/**
	 * 获取：重量固定基数
	 */
	public BigDecimal getBaseWeight() {
		return baseWeight;
	}
	/**
	 * 设置：面单成本
	 */
	public void setBaseBill(BigDecimal baseBill) {
		this.baseBill = baseBill;
	}
	/**
	 * 获取：面单成本
	 */
	public BigDecimal getBaseBill() {
		return baseBill;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	
	
}
