package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 行政区划表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-26 21:56:55
 */
@TableName("sys_area")
public class SysAreaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long id;
	/**
	 * 上级ID
	 */
	private Long parentId;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 邮编
	 */
	private String postCode;
	/**
	 * 地域编号
	 */
	private String areaCode;
	/**
	 * 区域等级id
	 */
	private Long lvId;
	/**
	 * 创建人
	 */
	private String createBy;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 最后更新人
	 */
	private String updateBy;
	/**
	 * 最后更新时间
	 */
	private Date updateTime;
	/**
	 * 版本号
	 */
	private Integer version;
	/**
	 * 是否有效；1：有效，2无效
	 */
	private Integer valid;

	/**
	 * 设置：主键
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：上级ID
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：上级ID
	 */
	public Long getParentId() {
		return parentId;
	}
	/**
	 * 设置：名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：邮编
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	/**
	 * 获取：邮编
	 */
	public String getPostCode() {
		return postCode;
	}
	/**
	 * 设置：地域编号
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	/**
	 * 获取：地域编号
	 */
	public String getAreaCode() {
		return areaCode;
	}
	/**
	 * 设置：区域等级id
	 */
	public void setLvId(Long lvId) {
		this.lvId = lvId;
	}
	/**
	 * 获取：区域等级id
	 */
	public Long getLvId() {
		return lvId;
	}
	/**
	 * 设置：创建人
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：创建人
	 */
	public String getCreateBy() {
		return createBy;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：最后更新人
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	/**
	 * 获取：最后更新人
	 */
	public String getUpdateBy() {
		return updateBy;
	}
	/**
	 * 设置：最后更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：最后更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：版本号
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}
	/**
	 * 获取：版本号
	 */
	public Integer getVersion() {
		return version;
	}
	/**
	 * 设置：是否有效；1：有效，2无效
	 */
	public void setValid(Integer valid) {
		this.valid = valid;
	}
	/**
	 * 获取：是否有效；1：有效，2无效
	 */
	public Integer getValid() {
		return valid;
	}
}
