package com.leon.mapper;

import com.leon.entity.SysUser;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SysPermissionMapper {
    

    List<SysUser> getSysUserList(Map<String, Object> contain);


    Set<String> findRoleNameByUserCode(String userCode);

    Set<String> findPermissionsByUserCode(String userCode);

    List<SysUser> getByPhone(String phone);
}
