package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 中转数据表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-31 09:24:05
 */
@TableName("exp_orders")
public class ExpOrdersEntity implements Serializable {
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
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 客户编码
	 */
	private String customerCode;
	/**
	 * 客户名称
	 */
	private String customerName;
	/**
	 * 重量
	 */
	private BigDecimal weight;
	/**
	 * y原重量
	 */
	private BigDecimal oldWeight;
	/**
	 * 快递费
	 */
	private BigDecimal money;
	/**
	 * 目的省
	 */
	private String destinationProvince;
	/**
	 * 网点编码
	 */
	private String dotCode;
	/**
	 * 网点名称
	 */
	private String dotName;
	/**
	 * 部门ID
	 */
	private Long deptId;
	
	
	private String des;

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
	 * 设置：创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateDate() {
		return createDate;
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
	 * 设置：重量
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	/**
	 * 获取：重量
	 */
	public BigDecimal getWeight() {
		return weight;
	}
	/**
	 * 设置：目的省
	 */
	public void setDestinationProvince(String destinationProvince) {
		this.destinationProvince = destinationProvince;
	}
	/**
	 * 获取：目的省
	 */
	public String getDestinationProvince() {
		return destinationProvince;
	}
	/**
	 * 设置：网点编码
	 */
	public void setDotCode(String dotCode) {
		this.dotCode = dotCode;
	}
	/**
	 * 获取：网点编码
	 */
	public String getDotCode() {
		return dotCode;
	}
	/**
	 * 设置：网点名称
	 */
	public void setDotName(String dotName) {
		this.dotName = dotName;
	}
	/**
	 * 获取：网点名称
	 */
	public String getDotName() {
		return dotName;
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
	/**
	 * 获取快递费
	 * @return
	 */
	public BigDecimal getMoney() {
		return money;
	}
	/**
	 * 设置快递费
	 * @return
	 */
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	
	
	public BigDecimal getOldWeight() {
		return oldWeight;
	}
	public void setOldWeight(BigDecimal oldWeight) {
		this.oldWeight = oldWeight;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	
	
	
}
