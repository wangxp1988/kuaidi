package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 菜鸟订单
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-25 13:44:46
 */
@TableName("exp_order_rookie")
public class ExpOrderRookieEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 订单号
	 */
	private String orderNumber;
	/**
	 * 运单号
	 */
	private String waybillNumber;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 
	 */
	private String orderStatus;
	/**
	 * 订单来源
	 */
	private String orderSoruce;
	/**
	 * 网点编号
	 */
	private String dotCode;
	/**
	 * 网点名称
	 */
	private String dotName;
	/**
	 * 客户编号
	 */
	private String customerCode;
	/**
	 * 客户名称
	 */
	private String customerName;
	/**
	 * 目的网点
	 */
	private String destinationDot;
	/**
	 * 目的分拨
	 */
	private String objectiveAllocation;
	/**
	 * 目的省份
	 */
	private String destinationProvince;
	/**
	 * 目的市
	 */
	private String destinationCity;
	/**
	 * 目的区
	 */
	private String destinationArea;
	/**
	 * 收件地址
	 */
	private String address;
	/**
	 * 部门ID
	 */
	private Long deptId;

	
	
	/**
	 * 
	 */
	public ExpOrderRookieEntity() {
		super();
	}
	/**
	 * @param orderNumber
	 * @param waybillNumber
	 * @param createDate
	 * @param orderStatus
	 * @param orderSoruce
	 * @param dotCode
	 * @param dotName
	 * @param customerCode
	 * @param customerName
	 * @param destinationDot
	 * @param objectiveAllocation
	 * @param destinationProvince
	 * @param destinationCity
	 * @param destinationArea
	 * @param address
	 * @param deptId
	 */
	public ExpOrderRookieEntity(String orderNumber, String waybillNumber, Date createDate, String orderStatus,
			String orderSoruce, String dotCode, String dotName, String customerCode, String customerName,
			String destinationDot, String objectiveAllocation, String destinationProvince, String destinationCity,
			String destinationArea, String address, Long deptId) {
		super();
		this.orderNumber = orderNumber;
		this.waybillNumber = waybillNumber;
		this.createDate = createDate;
		this.orderStatus = orderStatus;
		this.orderSoruce = orderSoruce;
		this.dotCode = dotCode;
		this.dotName = dotName;
		this.customerCode = customerCode;
		this.customerName = customerName;
		this.destinationDot = destinationDot;
		this.objectiveAllocation = objectiveAllocation;
		this.destinationProvince = destinationProvince;
		this.destinationCity = destinationCity;
		this.destinationArea = destinationArea;
		this.address = address;
		this.deptId = deptId;
	}
	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：订单号
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	/**
	 * 获取：订单号
	 */
	public String getOrderNumber() {
		return orderNumber;
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
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * 设置：
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	/**
	 * 获取：
	 */
	public String getOrderStatus() {
		return orderStatus;
	}
	/**
	 * 设置：订单来源
	 */
	public void setOrderSoruce(String orderSoruce) {
		this.orderSoruce = orderSoruce;
	}
	/**
	 * 获取：订单来源
	 */
	public String getOrderSoruce() {
		return orderSoruce;
	}
	/**
	 * 设置：网点编号
	 */
	public void setDotCode(String dotCode) {
		this.dotCode = dotCode;
	}
	/**
	 * 获取：网点编号
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
	 * 设置：客户编号
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	/**
	 * 获取：客户编号
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
	 * 设置：目的网点
	 */
	public void setDestinationDot(String destinationDot) {
		this.destinationDot = destinationDot;
	}
	/**
	 * 获取：目的网点
	 */
	public String getDestinationDot() {
		return destinationDot;
	}
	/**
	 * 设置：目的分拨
	 */
	public void setObjectiveAllocation(String objectiveAllocation) {
		this.objectiveAllocation = objectiveAllocation;
	}
	/**
	 * 获取：目的分拨
	 */
	public String getObjectiveAllocation() {
		return objectiveAllocation;
	}
	/**
	 * 设置：目的省份
	 */
	public void setDestinationProvince(String destinationProvince) {
		this.destinationProvince = destinationProvince;
	}
	/**
	 * 获取：目的省份
	 */
	public String getDestinationProvince() {
		return destinationProvince;
	}
	/**
	 * 设置：目的市
	 */
	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}
	/**
	 * 获取：目的市
	 */
	public String getDestinationCity() {
		return destinationCity;
	}
	/**
	 * 设置：目的区
	 */
	public void setDestinationArea(String destinationArea) {
		this.destinationArea = destinationArea;
	}
	/**
	 * 获取：目的区
	 */
	public String getDestinationArea() {
		return destinationArea;
	}
	/**
	 * 设置：收件地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：收件地址
	 */
	public String getAddress() {
		return address;
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
