package io.renren.modules.sys.entity;


import java.math.BigDecimal;
import java.io.Serializable;

/**
 * 价格表导出对象
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-25 09:29:17
 */
public class PriceEntity implements Serializable {

	/**
	 * 价格ID
	 */
	private Long id;
	/**
	 * 价格名称
	 */
	private String priceName;
	/**
	 * 省份名称
	 */
	private String provinceName;
	/**
	 * 重量
	 */
	private BigDecimal weight;
	/**
	 * 快递费用
	 */
	private BigDecimal money;
	
	private Long baseId;

	





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


	public Long getBaseId() {
		return baseId;
	}

	public void setBaseId(Long baseId) {
		this.baseId = baseId;
	}
	
	
	
}
