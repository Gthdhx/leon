package com.leon.controller.SystemController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leon.enums.ResultStatusCode;
import com.leon.shiro.realm.LoginType;
import com.leon.shiro.realm.UserToken;
import com.leon.utils.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    /**
     * 登陆页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/loginPage")
    public ModelAndView gotoLoginPage(ModelAndView model) {
        model.setViewName("login/login");
        return model;
    }

    /**
     * 用户密码登录
     * @param paraMap
     * @return
     */
    @PostMapping(value = "/login")
    public ResultUtil login(@RequestParam Map<String, Object> paraMap){
        String loginName = (String) paraMap.get("loginName");
        String pwd = (String) paraMap.get("pwd");
        UserToken token = new UserToken(LoginType.USER_PASSWORD, loginName, pwd);
        return shiroLogin(token);
    }

    /**
     * 首页
     * @param model
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView gotoIndexPage(ModelAndView model) {
        model.setViewName("system/home");
        return model;
    }

    /**
     * 手机验证码登录
     *      注：由于是demo演示，此处不添加发送验证码方法；
     *          正常操作：发送验证码至手机并且将验证码存放在redis中，登录的时候比较用户穿过来的验证码和redis中存放的验证码
     * @param phone
     * @param code
     * @return
     */
    @RequestMapping("phoneLogin")
    public ResultUtil phoneLogin(String phone, String code){
        // 此处phone替换了username，code替换了password
        UserToken token = new UserToken(LoginType.USER_PHONE, phone, code);
        return shiroLogin(token);
    }

    /**
     * 微信登录
     *      注：由于是demo演示，此处只接收一个code参数（微信会生成一个code，然后通过code获取openid等信息）
     *          其他根据个人实际情况添加参数
     * @param code
     * @return
     */
    @RequestMapping("wechatLogin")
    public ResultUtil wechatLogin(String code){
        // 此处假装code分别是username、password
        UserToken token = new UserToken(LoginType.WECHAT_LOGIN, code, code, code);
        return shiroLogin(token);
    }

    public ResultUtil shiroLogin(UserToken token){

        JSONObject json_test = new JSONObject();

        json_test.put("url","index");

        if(1==1){
            return new ResultUtil(ResultStatusCode.OK, json_test);
        }

        try {
            //登录不在该处处理，交由shiro处理
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);

            if (subject.isAuthenticated()) {
                JSON json = new JSONObject();
                ((JSONObject) json).put("token", subject.getSession().getId());

                return new ResultUtil(ResultStatusCode.OK, json);
            }else{
                return new ResultUtil(ResultStatusCode.SHIRO_ERROR);
            }
        }catch (IncorrectCredentialsException | UnknownAccountException e){
            e.printStackTrace();
            return new ResultUtil(ResultStatusCode.NOT_EXIST_USER_OR_ERROR_PWD);
        }catch (LockedAccountException e){
            return new ResultUtil(ResultStatusCode.USER_FROZEN);
        }catch (Exception e){
            return new ResultUtil(ResultStatusCode.SYSTEM_ERR);
        }
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping("/logout")
    public ResultUtil logout(){
        SecurityUtils.getSubject().logout();
        return new ResultUtil(ResultStatusCode.OK);
    }

}
