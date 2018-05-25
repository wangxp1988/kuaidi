package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 每日对账表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-25 13:44:46
 */
@TableName("exp_balance_account")
public class ExpBalanceAccountEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID 
	 */
	@TableId
	private Long id;
	/**
	 * 运单号
	 */
	private String waybillNumber;
	/**
	 * 寄件人
	 */
	private String sender;
	/**
	 * 网点
	 */
	private String branch;
	/**
	 * 寄件时间
	 */
	private Date sendTime;
	/**
	 * 
	 */
	private String sendProvince;
	/**
	 * 收件人
	 */
	private String recipient;
	/**
	 * 收件省份
	 */
	private String recipientProvince;
	/**
	 * 揽件业务员
	 */
	private String salesman;
	/**
	 * 月结客户名称
	 */
	private String customerName;
	/**
	 * 月结客户手机
	 */
	private String customerPhone;
	/**
	 * 实际重量
	 */
	private BigDecimal actualWeight;
	/**
	 * 部门ID
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
	 * 设置：寄件人
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}
	/**
	 * 获取：寄件人
	 */
	public String getSender() {
		return sender;
	}
	/**
	 * 设置：网点
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}
	/**
	 * 获取：网点
	 */
	public String getBranch() {
		return branch;
	}
	/**
	 * 设置：寄件时间
	 */
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	/**
	 * 获取：寄件时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-ddHH:mm:ss") 
    @JsonFormat(pattern="yyyy-MM-ddHH:mm:ss")
	public Date getSendTime() {
		return sendTime;
	}
	/**
	 * 设置：
	 */
	public void setSendProvince(String sendProvince) {
		this.sendProvince = sendProvince;
	}
	/**
	 * 获取：
	 */
	public String getSendProvince() {
		return sendProvince;
	}
	/**
	 * 设置：收件人
	 */
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	/**
	 * 获取：收件人
	 */
	public String getRecipient() {
		return recipient;
	}
	/**
	 * 设置：收件省份
	 */
	public void setRecipientProvince(String recipientProvince) {
		this.recipientProvince = recipientProvince;
	}
	/**
	 * 获取：收件省份
	 */
	public String getRecipientProvince() {
		return recipientProvince;
	}
	/**
	 * 设置：揽件业务员
	 */
	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}
	/**
	 * 获取：揽件业务员
	 */
	public String getSalesman() {
		return salesman;
	}
	/**
	 * 设置：月结客户名称
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * 获取：月结客户名称
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * 设置：月结客户手机
	 */
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	/**
	 * 获取：月结客户手机
	 */
	public String getCustomerPhone() {
		return customerPhone;
	}
	/**
	 * 设置：实际重量
	 */
	public void setActualWeight(BigDecimal actualWeight) {
		this.actualWeight = actualWeight;
	}
	/**
	 * 获取：实际重量
	 */
	public BigDecimal getActualWeight() {
		return actualWeight;
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
