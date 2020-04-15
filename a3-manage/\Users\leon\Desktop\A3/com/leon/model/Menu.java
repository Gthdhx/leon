package com.leon.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.IdType;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 * 
 *
 */
@TableName("sys_menu")
public class Menu implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 菜单编码 */
	private String menuCode;

	/** 系统编号 */
	private String systemCode;

	/** 菜单名称 */
	private String menuName;

	/** 上级菜单编码 */
	private String parentCode;

	/** 排序 */
	private String sort;

	/** 图标 */
	private String icon;

	/** 路由地址 */
	private String path;

	/** 菜单等级 0系统1模块、2页面、3按钮 */
	private Integer menuLevel;

	/** 创建时间 */
	private Date createTime;

	/** 创建人编码 */
	private String createCode;

	/** 修改时间 */
	private Date updateTime;

	/** 修改人编码 */
	private String updateCode;

	/** 状态 Y 有效 N 无效 */
	private String status;


	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMenuCode() {
		return this.menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getSystemCode() {
		return this.systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getParentCode() {
		return this.parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getSort() {
		return this.sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getMenuLevel() {
		return this.menuLevel;
	}

	public void setMenuLevel(Integer menuLevel) {
		this.menuLevel = menuLevel;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateCode() {
		return this.createCode;
	}

	public void setCreateCode(String createCode) {
		this.createCode = createCode;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateCode() {
		return this.updateCode;
	}

	public void setUpdateCode(String updateCode) {
		this.updateCode = updateCode;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
