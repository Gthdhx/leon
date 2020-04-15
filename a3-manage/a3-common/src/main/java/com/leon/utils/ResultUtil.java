package com.leon.utils;


import com.leon.enums.ResultStatusCode;

/**
 * 移动端api接口返回的数据模型
 * @author
 *
 */
public class ResultUtil {

	/**
	 * 返回结果状态值key
	 */
	private static final Integer CODE_S = 0;

	private static final Integer CODE_E = -1;

	private int code;		//返回的代码，0表示成功，其他表示失败

    private String msg;		//成功或失败时返回的错误信息

    private Object data;	//成功时返回的数据信息

	public ResultUtil(int code, String msg, Object data){
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public ResultUtil(ResultStatusCode resultStatusCode, Object data){
		this(resultStatusCode.getCode(), resultStatusCode.getMsg(), data);
	}

	public ResultUtil(int code, String msg){
		this(code, msg, null);
	}

	public ResultUtil(ResultStatusCode resultStatusCode){
		this(resultStatusCode, null);
	}

	public ResultUtil(){

	}

	/**
	 * 成功
	 *
	 * @param data
	 *            数据
	 * @return
	 */
	public static ResultUtil success(Object data) {
		ResultUtil result = new ResultUtil();
		result.setCode(CODE_S);
		result.setMsg("操作成功");
		result.setData(data);
		return result;
	}

	/**
	 * 失败
	 *
	 * @return
	 */
	public static ResultUtil error() {
		ResultUtil result = new ResultUtil();
		result.setCode(CODE_E);
		result.setMsg("系统繁忙");
		return result;
	}

	/**
	 * 失败
	 *
	 * @return
	 */
	public static ResultUtil error( String msg ) {
		ResultUtil result = new ResultUtil();
		result.setCode(CODE_E);
		result.setMsg(msg);
		return result;
	}

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
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
    
    
}
