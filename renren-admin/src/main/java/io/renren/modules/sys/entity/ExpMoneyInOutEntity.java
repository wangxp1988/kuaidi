package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 收支
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-28 15:39:17
 */
@TableName("exp_money_in_out")
public class ExpMoneyInOutEntity implements Serializable {
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
	 * 时间
	 */
	private Date createDate;
	/**
	 * 列名
	 */
	private String columnName;
	/**
	 * 金额
	 */
	private BigDecimal money;
	/**
	 * 部门ID
	 */
	private Long deptId;

	
	
	
	/**
	 * 
	 */
	public ExpMoneyInOutEntity() {
		super();
	}
	/**
	 * @param waybillNumber
	 * @param createDate
	 * @param columnName
	 * @param money
	 * @param deptId
	 */
	public ExpMoneyInOutEntity(String waybillNumber, Date createDate, String columnName, BigDecimal money,
			Long deptId) {
		super();
		this.waybillNumber = waybillNumber;
		this.createDate = createDate;
		this.columnName = columnName;
		this.money = money;
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
	 * 设置：时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd") 
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * 设置：列名
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	/**
	 * 获取：列名
	 */
	public String getColumnName() {
		return columnName;
	}
	/**
	 * 设置：金额
	 */
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	/**
	 * 获取：金额
	 */
	public BigDecimal getMoney() {
		return money;
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
