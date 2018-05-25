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
 * @date 2018-05-25 09:29:17
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
	 * 部门ID
	 */
	private Long deptId;

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
