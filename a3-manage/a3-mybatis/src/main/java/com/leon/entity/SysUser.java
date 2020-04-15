package com.leon.entity;

import lombok.Data;

@Data
public class SysUser {

    private String loginName;

    private String phone;

    /** 用户编码 */
    private String userCode;

    /** 用户密码 **/
    private String password;

    /** 状态 */
    private String status;


}
