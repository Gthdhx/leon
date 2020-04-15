package com.leon.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.leon.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 *
 * Enroll 表数据库控制层接口
 *
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据用户角色获取菜单
     * @param contain
     * @return
     */
    List<JSONObject> getSysMenuList(Map<String, Object> contain);

    /**
     * 根据条件获取菜单列表
     * @param contain
     * @return
     */
    List<JSONObject> getMenuList(Map<String, Object> contain);


    List<JSONObject> getSysMenuTreeList(Map<String, Object> contain);

}