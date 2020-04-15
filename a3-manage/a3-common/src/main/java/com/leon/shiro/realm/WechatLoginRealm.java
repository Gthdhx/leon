package com.leon.shiro.realm;

import com.leon.entity.SysUser;
import com.leon.mapper.SysPermissionMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 微信登录realm
 */
@Slf4j
public class WechatLoginRealm extends AuthorizingRealm {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(AuthorizationRealm.class);

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public String getName() {
        return LoginType.WECHAT_LOGIN.getType();
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        if (token instanceof UserToken) {
            return ((UserToken) token).getLoginType() == LoginType.WECHAT_LOGIN;
        } else {
            return false;
        }
    }

    @Override
    public void setAuthorizationCacheName(String authorizationCacheName) {
        super.setAuthorizationCacheName(authorizationCacheName);
    }

    @Override
    protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 认证信息.(身份验证) : Authentication 是用来验证用户身份
     *
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        log.info("---------------- 微信登录 ----------------------");
        UserToken token = (UserToken) authcToken;
        String code = token.getCode();

        String openid = getOpenid(code);

        if(StringUtils.isEmpty(openid)){
            log.debug("微信授权登录失败，未获得openid");
            throw new AuthenticationException();
        }

        // 从数据库获取对应用户名密码的用户
        Map<String, Object> contain = new HashMap<>();
        List<SysUser> sysUserList = sysPermissionMapper.getSysUserList(contain);
        if(sysUserList == null || sysUserList.isEmpty()){
            // TODO 获取微信昵称、头像等信息，并完成注册用户，此处省略
        }
        SysUser sysUser = sysUserList.get(0);

        if(sysUser == null){
            // TODO 获取微信昵称、头像等信息，并完成注册用户，此处省略
        }
        // 用户为禁用状态
        if("SD".equals(sysUser.getStatus())){
            throw new DisabledAccountException();
        }

        // 完成登录，此处已经不需要做密码校验
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                sysUser, //用户
                code, //密码
                getName()  //realm name
        );
        return authenticationInfo;
    }

    private String getOpenid(String code){
        // 这里假装是一个通过code获取openid的方法，具体实现由各位自己去实现，此处不做扩展
        if(StringUtils.isNotEmpty(code)){
            return "sdfuh81238917jhoijiosdsgsdfljiofds";
        }
        return null;
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("---------------- 执行 Shiro 权限获取 ---------------------");
        Object principal = principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        if (principal instanceof SysUser) {
            SysUser sysUser = (SysUser) principal;
            Set<String> roles = sysPermissionMapper.findRoleNameByUserCode(sysUser.getUserCode());
            authorizationInfo.addRoles(roles);

            Set<String> permissions = sysPermissionMapper.findPermissionsByUserCode(sysUser.getUserCode());
            authorizationInfo.addStringPermissions(permissions);
        }
        logger.info("---- 获取到以下权限 -----");
        logger.info(authorizationInfo.getStringPermissions().toString());
        logger.info("---------------- Shiro 权限获取成功 ----------------------");
        return authorizationInfo;
    }

}
