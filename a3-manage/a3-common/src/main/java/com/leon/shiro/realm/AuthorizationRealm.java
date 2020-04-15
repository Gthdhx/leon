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
 * 统一角色授权控制策略
 */
@Slf4j
public class AuthorizationRealm extends AuthorizingRealm {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(AuthorizationRealm.class);

    //如果项目中用到了事物，@Autowired注解会使事物失效，可以自己用get方法获取值
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    /**
     * 认证信息.(身份验证) : Authentication 是用来验证用户身份
     *
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        logger.info("---------------- 执行 Shiro 凭证认证 ----------------------");
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String userName = token.getUsername();
        String password = String.valueOf(token.getPassword());
        SysUser sysPermission = new SysUser();
        sysPermission.setLoginName(userName);
        sysPermission.setPassword(password);

        Map<String,Object> contain = new HashMap<>();

        contain.put("userName",userName);
        contain.put("password",password);

        // 从数据库获取对应用户名密码的用户
        List<SysUser> sysUserList = sysPermissionMapper.getSysUserList(contain);

        if(sysUserList == null || sysUserList.isEmpty()){
            throw new UnknownAccountException();
        }

        SysUser sysUser = sysUserList.get(0);

        if (sysUser != null) {
            // 用户为禁用状态
            if ("SD".equals(sysUser.getStatus())) {
                throw new DisabledAccountException();
            }
            logger.info("---------------- Shiro 凭证认证成功 ----------------------");
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
        logger.info("---- 获取到以下权限 ----");
        logger.info(authorizationInfo.getStringPermissions().toString());
        logger.info("---------------- Shiro 权限获取成功 ----------------------");
        return authorizationInfo;
    }
}
