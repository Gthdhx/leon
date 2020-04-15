package com.leon.entity;



/**
 * 编码规则表
 * 
 * @author Administrator
 *
 */
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.Api;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Api("编码规则表")
@Data
@TableName("sys_code")
public class SysCode implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 主键id */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 编码类型,例如 SQ */
	private String type;

	/** 前缀 */
	private String prefix;

	/** 中段时间格式(%Y%m%d%h%i%s) */
	private String middle;

	/** 后缀初始值 */
	private Integer suffixValue;

	/** 增长值 */
	private Integer growthValue;

	/** 后缀最大值 */
	private Integer maxValue;

	/** 当前值 */
	private Integer currentValue;

	/** 1代表有效 */
	private Integer status;

	/** 描述 */
	private String remark;

	/** 创建人Id */
	private Integer createId;

	/** 创建时间 */
	private Date createTime;

	/** 更新人 */
	private Integer updateId;

	/** 更新时间 */
	private Date updateTime;

}
