package com.leon.service.system;

import com.leon.entity.SysUser;

import java.util.Map;
import java.util.Set;

public interface SysPermissionService {

    SysUser getSysUser(Map<String, Object> contain);

    Set<String> findRoleNameByUserCode(String userCode);

    Set<String> findPermissionsByUserCode(String userCode);

}
