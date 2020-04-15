package com.leon.service.system;

import com.alibaba.fastjson.JSONObject;
import com.leon.entity.SysMenu;
import com.leon.utils.LayUiListUtil;
import com.leon.utils.ResultUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SysMenuService {

    List<JSONObject> getSysMenuList (Map<String, Object> contain);

    ResultUtil insertMenu (JSONObject menu);

    LayUiListUtil getMenuList(Map<String, Object> contain);

    JSONObject getMenuByMap(Map<String, Object> contain);

    List<JSONObject> getSysMenuTreeList(Map<String, Object> contain);
}
