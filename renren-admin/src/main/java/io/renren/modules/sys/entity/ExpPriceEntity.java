package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Transient;

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
	 * 省份编码和名称的组合使用-来划分
	 */
	@TableField(exist = false)
	private String provinceNumAndName;
	/**
	 * 重量
	 */
	private BigDecimal weight;
	/**
	 * 快递费用
	 */
	private BigDecimal money;
	/**
	 * 部门ID
	 */
	private Long deptId;

	
	/**
	 * 
	 */
	public ExpPriceEntity() {
		super();
	}
	  
	
	
	/**
	 * @param id
	 * @param priceName
	 * @param provinceName
	 * @param weight
	 * @param money
	 * @param deptId
	 */
	public ExpPriceEntity(Long id, String priceName, String provinceName, BigDecimal weight, BigDecimal money,
			Long deptId) {
		super();
		this.id = id;
		this.priceName = priceName;
		this.provinceName = provinceName;
		this.weight = weight;
		this.money = money;
		this.deptId = deptId;
	}





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
	public String getProvinceNumAndName() {
		return provinceNumAndName;
	}
	public void setProvinceNumAndName(String provinceNumAndName) {
		this.provinceNumAndName = provinceNumAndName;
	}
	
	
	
	
}
