package com.leon.service.system;

import com.leon.entity.SysUser;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service("sysPermissionService")
public class SysPermissionServiceImpl implements SysPermissionService {

    @Override
    public SysUser getSysUser(Map<String, Object> contain) {
        return null;
    }

    @Override
    public Set<String> findRoleNameByUserCode(String userCode) {
        return null;
    }

    @Override
    public Set<String> findPermissionsByUserCode(String userCode) {
        return null;
    }

}
