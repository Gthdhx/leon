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

import java.util.List;
import java.util.Set;

/**
 * 手机验证码登录realm
 */
@Slf4j
public class UserPhoneRealm extends AuthorizingRealm {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(AuthorizationRealm.class);

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public String getName() {
        return LoginType.USER_PHONE.getType();
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        if (token instanceof UserToken) {
            return ((UserToken) token).getLoginType() == LoginType.USER_PHONE;
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

        log.info("---------------- 手机验证码登录 ----------------------");

        UserToken token = (UserToken) authcToken;
        String phone = token.getUsername();
        // 手机验证码
        String validCode = String.valueOf(token.getPassword());

        // 这里假装从redis中获取了验证码为 123456，并对比密码是否正确
        if(!"123456".equals(validCode)){
            log.debug("验证码错误，手机号为：{}", phone);
            throw new IncorrectCredentialsException();
        }

        List<SysUser> userList = sysPermissionMapper.getByPhone(phone);
        if(userList!=null || userList.isEmpty() ){
            throw new UnknownAccountException();
        }

        SysUser sysUser = userList.get(0);

        if(sysUser == null){
            throw new UnknownAccountException();
        }
        // 用户为禁用状态
        if("SD".equals(sysUser.getStatus())){
            throw new DisabledAccountException();
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                sysUser, //用户
                validCode, //密码
                getName()  //realm name
        );
        return authenticationInfo;
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
