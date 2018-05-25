package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 价格表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-25 09:29:17
 */
@TableName("exp_price")
public class ExpPriceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 价格ID
	 */
	@TableId
	private Long id;
	/**
	 * 价格名称
	 */
	private String priceName;
	/**
	 * 省份编码
	 */
	private String province;
	/**
	 * 省份名称
	 */
	private String provinceName;
	/**
	 * 重量
	 */
	private Float weight;
	/**
	 * 快递费用
	 */
	private BigDecimal money;
	/**
	 * 部门ID
	 */
	private Long deptId;

	/**
	 * 设置：价格ID
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：价格ID
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：价格名称
	 */
	public void setPriceName(String priceName) {
		this.priceName = priceName;
	}
	/**
	 * 获取：价格名称
	 */
	public String getPriceName() {
		return priceName;
	}
	/**
	 * 设置：省份编码
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * 获取：省份编码
	 */
	public String getProvince() {
		return province;
	}
	/**
	 * 设置：省份名称
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	/**
	 * 获取：省份名称
	 */
	public String getProvinceName() {
		return provinceName;
	}
	/**
	 * 设置：重量
	 */
	public void setWeight(Float weight) {
		this.weight = weight;
	}
	/**
	 * 获取：重量
	 */
	public Float getWeight() {
		return weight;
	}
	/**
	 * 设置：快递费用
	 */
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	/**
	 * 获取：快递费用
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
