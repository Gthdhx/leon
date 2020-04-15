package com.leon.utils;


import com.leon.enums.ResultStatusCode;

/**
 * 移动端api接口返回的数据模型
 * @author
 *
 */
public class LayUiListUtil {

	private Integer code;		//返回的代码，0表示成功，其他表示失败

    private String msg;		//成功或失败时返回的错误信息

    private Object data;	//成功时返回的数据信息

	private Integer count;

	public LayUiListUtil(Integer code, String msg, Object data,Integer count){
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.count = count;
	}

	public LayUiListUtil(Integer code, String msg, Object data){
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public LayUiListUtil(ResultStatusCode resultStatusCode, Object data){
		this(resultStatusCode.getCode(), resultStatusCode.getMsg(), data);
	}

	public LayUiListUtil(Integer code, String msg){
		this(code, msg, null);
	}

	public LayUiListUtil(ResultStatusCode resultStatusCode){
		this(resultStatusCode, null);
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
