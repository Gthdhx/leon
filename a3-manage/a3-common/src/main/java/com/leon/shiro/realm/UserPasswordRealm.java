package com.leon.shiro.realm;

import com.leon.entity.SysUser;
import com.leon.mapper.SysPermissionMapper;
import lombok.extern.slf4j.Slf4j;
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
 * 用户密码登录realm
 */
@Slf4j
public class UserPasswordRealm extends AuthorizingRealm {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(AuthorizationRealm.class);

    @Autowired
    private SysPermissionMapper sysPermissionMapper;


    @Override
    public String getName() {
        return LoginType.USER_PASSWORD.getType();
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        if (token instanceof UserToken) {
            return ((UserToken) token).getLoginType() == LoginType.USER_PASSWORD;
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
        log.info("---------------- 用户密码登录 ----------------------");
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String name = token.getUsername();

        // 从数据库获取对应用户名密码的用户
        Map<String, Object> contain = new HashMap<>();
        List<SysUser> sysUserList = sysPermissionMapper.getSysUserList(contain);
        if(sysUserList == null || sysUserList.isEmpty()){
            throw new UnknownAccountException();
        }
        SysUser sysUser = sysUserList.get(0);

        if (sysUser != null) {
            // 用户为禁用状态
            if ("N".equals(sysUser.getStatus())) {
                throw new DisabledAccountException();
            }
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    sysUser, //用户
                    sysUser.getPassword(), //密码
                    getName()  //realm name
            );
            return authenticationInfo;
        }
        throw new UnknownAccountException();
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
