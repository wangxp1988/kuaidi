package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户信息
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-27 21:00:52
 */
@TableName("exp_customer")
public class ExpCustomerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 客户ID
	 */
	@TableId
	private Long id;
	/**
	 * 客户编码
	 */
	private String code;
	/**
	 * 客户名称
	 */
	private String name;
	/**
	 * 客户类型
	 */
	private String type;
	/**
	 * 联系人
	 */
	private String contacts;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 价格表名称
	 */
	private String priceName;
	/**
	 * 付款客户ID
	 */
	private String paymentId;
	/**
	 * 付款客户名称
	 */
	private String paymentName;
	/**
	 * 部门ID
	 */
	private Long deptId;

	
	
	/**
	 * 
	 */
	public ExpCustomerEntity() {
		super();
	}
	/**
	 * @param code
	 * @param name
	 * @param type
	 * @param contacts
	 * @param phone
	 * @param address
	 * @param priceName
	 * @param paymentId
	 * @param paymentName
	 * @param deptId
	 */
	public ExpCustomerEntity(String code, String name, String type, String contacts, String phone, String address,
			String priceName, String paymentId, String paymentName, Long deptId) {
		super();
		this.code = code;
		this.name = name;
		this.type = type;
		this.contacts = contacts;
		this.phone = phone;
		this.address = address;
		this.priceName = priceName;
		this.paymentId = paymentId;
		this.paymentName = paymentName;
		this.deptId = deptId;
	}
	/**
	 * 设置：客户ID
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：客户ID
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：客户编码
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：客户编码
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置：客户名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：客户名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：客户类型
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：客户类型
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：联系人
	 */
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	/**
	 * 获取：联系人
	 */
	public String getContacts() {
		return contacts;
	}
	/**
	 * 设置：电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：电话
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：地址
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：价格表名称
	 */
	public void setPriceName(String priceName) {
		this.priceName = priceName;
	}
	/**
	 * 获取：价格表名称
	 */
	public String getPriceName() {
		return priceName;
	}
	/**
	 * 设置：付款客户ID
	 */
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	/**
	 * 获取：付款客户ID
	 */
	public String getPaymentId() {
		return paymentId;
	}
	/**
	 * 设置：付款客户名称
	 */
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}
	/**
	 * 获取：付款客户名称
	 */
	public String getPaymentName() {
		return paymentName;
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
