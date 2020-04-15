package com.leon.controller.SystemController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leon.enums.ResultStatusCode;
import com.leon.service.system.SysMenuService;
import com.leon.shiro.realm.LoginType;
import com.leon.shiro.realm.UserToken;
import com.leon.utils.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("/getMenuList")
    public List<JSONObject> getMenuList(){
        Map<String,Object> contain = new HashMap<>();
        return  sysMenuService.getSysMenuList(contain);
    }


}
