package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 账单收支
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-06-27 22:46:59
 */
@TableName("exp_bill_payment")
public class ExpBillPaymentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Long id;
	/**
	 * 账单期间
	 */
	private String billingPeriod;
	/**
	 * 客户类型
	 */
	private String customerType;
	/**
	 * 客户编码
	 */
	private String customerCode;
	/**
	 * 客户名称
	 */
	private String customerName;
	/**
	 * 应收运费
	 */
	private BigDecimal receivable;
	/**
	 * 已付运费
	 */
	private BigDecimal paid;
	/**
	 * 未付运费
	 */
	private BigDecimal unpaid;
	/**
	 * 开始时间
	 */
	private Date startDate;
	/**
	 * 结束时间
	 */
	private Date endDate;
	/**
	 * 
	 */
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
	 * 设置：账单期间
	 */
	public void setBillingPeriod(String billingPeriod) {
		this.billingPeriod = billingPeriod;
	}
	/**
	 * 获取：账单期间
	 */
	public String getBillingPeriod() {
		return billingPeriod;
	}
	/**
	 * 设置：客户类型
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	/**
	 * 获取：客户类型
	 */
	public String getCustomerType() {
		return customerType;
	}
	/**
	 * 设置：客户编码
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	/**
	 * 获取：客户编码
	 */
	public String getCustomerCode() {
		return customerCode;
	}
	/**
	 * 设置：客户名称
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * 获取：客户名称
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * 设置：应收运费
	 */
	public void setReceivable(BigDecimal receivable) {
		this.receivable = receivable;
	}
	/**
	 * 获取：应收运费
	 */
	public BigDecimal getReceivable() {
		return receivable;
	}
	/**
	 * 设置：已付运费
	 */
	public void setPaid(BigDecimal paid) {
		this.paid = paid;
	}
	/**
	 * 获取：已付运费
	 */
	public BigDecimal getPaid() {
		return paid;
	}
	/**
	 * 设置：未付运费
	 */
	public void setUnpaid(BigDecimal unpaid) {
		this.unpaid = unpaid;
	}
	/**
	 * 获取：未付运费
	 */
	public BigDecimal getUnpaid() {
		return unpaid;
	}
	/**
	 * 设置：开始时间
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * 获取：开始时间
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * 设置：结束时间
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * 获取：结束时间
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * 设置：
	 */
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	/**
	 * 获取：
	 */
	public Long getDeptId() {
		return deptId;
	}
}
