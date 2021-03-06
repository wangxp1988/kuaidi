package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 日常收支
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-27 00:02:48
 */
@TableName("exp_general_in_out")
public class ExpGeneralInOutEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Long id;
	/**
	 * 客户编码
	 */
	private String customerId;
	/**
	 * 运单号
	 */
	private String waybillNumber;
	/**
	 * 客户
	 */
	private String consumer;
	/**
	 * 款项说明
	 */
	private String moneyDetail;
	/**
	 * 收入金额
	 */
	private BigDecimal moneyIn;
	/**
	 * 支出金额
	 */
	private BigDecimal moneyOut;
	/**
	 * 账户
	 */
	private String account;
	/**
	 * 备注
	 */
	private String remarks;
	/**
	 * 记账日期
	 */
	private Date createTime;
	/**
	 * 部门ID
	 */
	private Long deptId;
	
	
	

	/**
	 * 
	 */
	public ExpGeneralInOutEntity() {
		super();
	}
	/**
	 * @param customerId
	 * @param waybillNumber
	 * @param consumer
	 * @param moneyDetail
	 * @param moneyIn
	 * @param moneyOut
	 * @param account
	 * @param remarks
	 * @param createTime
	 * @param deptId
	 */
	public ExpGeneralInOutEntity(String customerId, String waybillNumber, String consumer, String moneyDetail,
			BigDecimal moneyIn, BigDecimal moneyOut, String account, String remarks, Date createTime, Long deptId) {
		super();
		this.customerId = customerId;
		this.waybillNumber = waybillNumber;
		this.consumer = consumer;
		this.moneyDetail = moneyDetail;
		this.moneyIn = moneyIn;
		this.moneyOut = moneyOut;
		this.account = account;
		this.remarks = remarks;
		this.createTime = createTime;
		this.deptId = deptId;
	}
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
	 * 设置：客户编码
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	/**
	 * 获取：客户编码
	 */
	public String getCustomerId() {
		return customerId;
	}
	/**
	 * 设置：运单号
	 */
	public void setWaybillNumber(String waybillNumber) {
		this.waybillNumber = waybillNumber;
	}
	/**
	 * 获取：运单号
	 */
	public String getWaybillNumber() {
		return waybillNumber;
	}
	/**
	 * 设置：客户
	 */
	public void setConsumer(String consumer) {
		this.consumer = consumer;
	}
	/**
	 * 获取：客户
	 */
	public String getConsumer() {
		return consumer;
	}
	/**
	 * 设置：款项说明
	 */
	public void setMoneyDetail(String moneyDetail) {
		this.moneyDetail = moneyDetail;
	}
	/**
	 * 获取：款项说明
	 */
	public String getMoneyDetail() {
		return moneyDetail;
	}
	/**
	 * 设置：收入金额
	 */
	public void setMoneyIn(BigDecimal moneyIn) {
		this.moneyIn = moneyIn;
	}
	/**
	 * 获取：收入金额
	 */
	public BigDecimal getMoneyIn() {
		return moneyIn;
	}
	/**
	 * 设置：支出金额
	 */
	public void setMoneyOut(BigDecimal moneyOut) {
		this.moneyOut = moneyOut;
	}
	/**
	 * 获取：支出金额
	 */
	public BigDecimal getMoneyOut() {
		return moneyOut;
	}
	/**
	 * 设置：账户
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * 获取：账户
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * 设置：备注
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * 获取：备注
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * 设置：记账日期
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：记账日期
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd") 
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：部门ID
	 */
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	/**
	 * 获取：部门ID
	 */
	public Long getDeptId() {
		return deptId;
	}
}
