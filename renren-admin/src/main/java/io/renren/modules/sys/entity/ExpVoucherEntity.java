package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 凭证管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-06-03 12:07:07
 */
@TableName("exp_voucher")
public class ExpVoucherEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Long id;
	/**
	 * 凭证摘要
	 */
	private String voucherRemark;
	/**
	 * 凭证编码
	 */
	private String voucherCode;
	/**
	 * 二级编码
	 */
	private String twoLevelCoding;
	/**
	 * 二级名称
	 */
	private String twoLevelName;
	/**
	 * 客户名称
	 */
	private String customerName;
	/**
	 * 运单号
	 */
	private String waybillNumber;
	/**
	 * 目的网点
	 */
	private String destinationDot;
	/**
	 * 借方金额
	 */
	private BigDecimal debtorMoney;
	/**
	 * 贷方金额
	 */
	private BigDecimal lenderMoney;
	/**
	 * 借方重量
	 */
	private BigDecimal debtorWeight;
	/**
	 * 贷方重量
	 */
	private BigDecimal lenderWeight;
	/**
	 * 客户编码
	 */
	private String customerCode;
	/**
	 * 创建时间
	 */
	private Date createDate;
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
	 * 设置：凭证摘要
	 */
	public void setVoucherRemark(String voucherRemark) {
		this.voucherRemark = voucherRemark;
	}
	/**
	 * 获取：凭证摘要
	 */
	public String getVoucherRemark() {
		return voucherRemark;
	}
	
	public String getVoucherCode() {
		return voucherCode;
	}
	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}
	/**
	 * 设置：二级编码
	 */
	public void setTwoLevelCoding(String twoLevelCoding) {
		this.twoLevelCoding = twoLevelCoding;
	}
	/**
	 * 获取：二级编码
	 */
	public String getTwoLevelCoding() {
		return twoLevelCoding;
	}
	/**
	 * 设置：二级名称
	 */
	public void setTwoLevelName(String twoLevelName) {
		this.twoLevelName = twoLevelName;
	}
	/**
	 * 获取：二级名称
	 */
	public String getTwoLevelName() {
		return twoLevelName;
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
	 * 设置：借方金额
	 */
	public void setDebtorMoney(BigDecimal debtorMoney) {
		this.debtorMoney = debtorMoney;
	}
	/**
	 * 获取：借方金额
	 */
	public BigDecimal getDebtorMoney() {
		return debtorMoney;
	}
	/**
	 * 设置：贷方金额
	 */
	public void setLenderMoney(BigDecimal lenderMoney) {
		this.lenderMoney = lenderMoney;
	}
	/**
	 * 获取：贷方金额
	 */
	public BigDecimal getLenderMoney() {
		return lenderMoney;
	}
	/**
	 * 设置：借方重量
	 */
	public void setDebtorWeight(BigDecimal debtorWeight) {
		this.debtorWeight = debtorWeight;
	}
	/**
	 * 获取：借方重量
	 */
	public BigDecimal getDebtorWeight() {
		return debtorWeight;
	}
	/**
	 * 设置：贷方重量
	 */
	public void setLenderWeight(BigDecimal lenderWeight) {
		this.lenderWeight = lenderWeight;
	}
	/**
	 * 获取：贷方重量
	 */
	public BigDecimal getLenderWeight() {
		return lenderWeight;
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
