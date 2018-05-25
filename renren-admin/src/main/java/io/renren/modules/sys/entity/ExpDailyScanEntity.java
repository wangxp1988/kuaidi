package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 每日扫描
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-25 13:44:46
 */
@TableName("exp_daily_scan")
public class ExpDailyScanEntity implements Serializable {
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
	 * 扫描网点
	 */
	private String branch;
	/**
	 * 扫描人
	 */
	private String person;
	/**
	 * 扫描日期
	 */
	private Date createDate;
	/**
	 * 收件人
	 */
	private String recipient;
	/**
	 * 寄件人
	 */
	private String sender;
	/**
	 * 网点称重
	 */
	private BigDecimal weight;
	/**
	 * 重量来源
	 */
	private String weightSourse;
	/**
	 * 数据来源
	 */
	private String dataSourse;
	/**
	 * 设备编号
	 */
	private String deviceNumber;
	/**
	 * 部门ID
	 */
	private Long deptId;
	/**
	 * 
	 */
	public ExpDailyScanEntity() {
		super();
	}
	/**
	 * @param waybillNumber
	 * @param branch
	 * @param person
	 * @param createDate
	 * @param recipient
	 * @param sender
	 * @param weight
	 * @param weightSourse
	 * @param dataSourse
	 * @param deviceNumber
	 * @param deptId
	 */
	public ExpDailyScanEntity(String waybillNumber, String branch, String person, Date createDate, String recipient,
			String sender, BigDecimal weight, String weightSourse, String dataSourse, String deviceNumber,
			Long deptId) {
		super();
		this.waybillNumber = waybillNumber;
		this.branch = branch;
		this.person = person;
		this.createDate = createDate;
		this.recipient = recipient;
		this.sender = sender;
		this.weight = weight;
		this.weightSourse = weightSourse;
		this.dataSourse = dataSourse;
		this.deviceNumber = deviceNumber;
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
	 * 设置：扫描网点
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}
	/**
	 * 获取：扫描网点
	 */
	public String getBranch() {
		return branch;
	}
	/**
	 * 设置：扫描人
	 */
	public void setPerson(String person) {
		this.person = person;
	}
	/**
	 * 获取：扫描人
	 */
	public String getPerson() {
		return person;
	}
	/**
	 * 设置：扫描日期
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：扫描日期
	 */
	@DateTimeFormat(pattern="yyyy-MM-ddHH:mm:ss") 
    @JsonFormat(pattern="yyyy-MM-ddHH:mm:ss")
	public Date getCreateDate() {
		return createDate;
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
	 * 设置：网点称重
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	/**
	 * 获取：网点称重
	 */
	public BigDecimal getWeight() {
		return weight;
	}
	/**
	 * 设置：重量来源
	 */
	public void setWeightSourse(String weightSourse) {
		this.weightSourse = weightSourse;
	}
	/**
	 * 获取：重量来源
	 */
	public String getWeightSourse() {
		return weightSourse;
	}
	/**
	 * 设置：数据来源
	 */
	public void setDataSourse(String dataSourse) {
		this.dataSourse = dataSourse;
	}
	/**
	 * 获取：数据来源
	 */
	public String getDataSourse() {
		return dataSourse;
	}
	/**
	 * 设置：设备编号
	 */
	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}
	/**
	 * 获取：设备编号
	 */
	public String getDeviceNumber() {
		return deviceNumber;
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
