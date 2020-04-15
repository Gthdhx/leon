package com.leon.controller;

import com.leon.enums.ResultStatusCode;
import com.leon.utils.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/common")
@RestController
public class CommonController {

    /**
     * 未授权跳转方法
     * @return
     */
    @RequestMapping("/unauth")
    public ResultUtil unauth(){
        SecurityUtils.getSubject().logout();
        return new ResultUtil(ResultStatusCode.UNAUTHO_ERROR);
    }

    /**
     * 被踢出后跳转方法
     * @return
     */
    @RequestMapping("/kickOut")
    public ResultUtil kickOut(){
        return new ResultUtil(ResultStatusCode.INVALID_TOKEN);
    }
}
