package com.leon.service.system;

import com.alibaba.fastjson.JSONObject;
import com.leon.druid.config.DataSource;
import com.leon.druid.config.DataSourceEnum;
import com.leon.entity.SysMenu;
import com.leon.enums.ResultStatusCode;
import com.leon.mapper.SysMenuMapper;
import com.leon.utils.LayUiListUtil;
import com.leon.utils.ResultUtil;
import com.leon.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysCodeService sysCodeService;

    @Override
    @DataSource(DataSourceEnum.GET)
    @Transactional
    public List<JSONObject> getSysMenuList(Map<String, Object> contain) {

        List<JSONObject> menuList = sysMenuMapper.getSysMenuList(contain);

        if(menuList == null || menuList.isEmpty()){
            return new ArrayList<>();
        }
        List<JSONObject> results = new ArrayList<>();
        Map<String,List<JSONObject>> menuListMap = new HashMap<>();
        for(JSONObject menu : menuList){
            String level = menu.getString("menu_level");
            String menu_code = menu.getString("menu_code");
            String parent_code = menu.getString("parent_code");
            if( "1".equals(level) && StringUtils.isNotEmpty(menu_code) ){
                results.add(menu);
            }
            if( "2".equals(level) && StringUtils.isNotEmpty(parent_code) ){
                List<JSONObject> menuList_map = menuListMap.get(parent_code);
                if( menuList_map == null || menuList_map.isEmpty()){
                    List<JSONObject> menuList_new = new ArrayList<>();
                    menuList_new.add(menu);
                    menuListMap.put(parent_code,menuList_new);
                } else {
                    menuList_map.add(menu);
                }
            }
        }

        if(results == null || results.isEmpty()){
            return new ArrayList<>();
        }

        for(JSONObject menu : results){
            String menu_code = menu.getString("menu_code");
            if(StringUtils.isEmpty(menu_code)){
                continue;
            }
            menu.put("spread","true");
            List<JSONObject> menuList_map = menuListMap.get(menu_code);
            if(menuList_map !=null && menuList_map.size()>0){
                menu.put("children",menuList_map);
            }
        }
        return results;
    }



    @Override
    @DataSource(DataSourceEnum.POST)
    @Transactional
    public ResultUtil insertMenu (JSONObject menu) {

        String menuCode = sysCodeService.getSysCode("menuCode");

        if(StringUtil.isEmpty(menuCode)){
            return ResultUtil.error("获取编码失败");
        }

       String menuName = menu.getString("menuName");
       String parentCode = menu.getString("parentCode");
       String sort = menu.getString("sort");
       String icon = menu.getString("icon");
       String path = menu.getString("path");
       Integer menuLevel = menu.getInteger("menuLevel");

        Map<String, Object> contain_System = new HashMap<>();
        contain_System.put("menu_level","0");
        List<SysMenu> menuList_System = sysMenuMapper.selectByMap(contain_System);
        String systemCode = "";
        if(menuList_System!=null && menuList_System.size()>0){
            SysMenu menu_sys = menuList_System.get(0);
            systemCode = menu_sys.getMenuCode();
            if(StringUtil.isEmpty(systemCode)){
                return ResultUtil.error("获取系统编码失败");
            }
        }

        if( StringUtil.isEmpty(menuCode) || StringUtil.isEmpty(menuName) || StringUtil.isEmpty(parentCode) ||
            StringUtil.isEmpty(sort) || StringUtil.isEmpty(icon) || StringUtil.isEmpty(path) ||
            menuLevel == null ){

        }

        SysMenu sysMenu = new SysMenu();

        sysMenu.setCreateCode("U00001");
        sysMenu.setCreateTime(new Date());
        sysMenu.setIcon(icon);
        sysMenu.setMenuName(menuName);
        sysMenu.setMenuCode(menuCode);
        sysMenu.setSystemCode(systemCode);
        sysMenu.setParentCode(parentCode);
        sysMenu.setMenuLevel(menuLevel);
        sysMenu.setPath(path);
        sysMenu.setSort(sort);
        sysMenu.setStatus("Y");

        sysMenuMapper.insert(sysMenu);

        return ResultUtil.success("操作成功");
    }

    @Override
    @DataSource(DataSourceEnum.POST)
    @Transactional
    public LayUiListUtil getMenuList(Map<String, Object> contain) {
        try {
            return new LayUiListUtil(ResultStatusCode.OK,sysMenuMapper.selectByMap(contain));
        }catch (Exception e){
            e.printStackTrace();
            return new LayUiListUtil(400,"系统异常");
        }
    }

    @Override
    public JSONObject getMenuByMap(Map<String, Object> contain) {
        List<JSONObject> menuList = sysMenuMapper.getMenuList(contain);
        if(menuList!=null && menuList.size()>0){
            return menuList.get(0);
        }
        return new JSONObject();
    }


    @Override
    @DataSource(DataSourceEnum.GET)
    @Transactional
    public List<JSONObject> getSysMenuTreeList(Map<String, Object> contain) {

        List<JSONObject> menuList = sysMenuMapper.getSysMenuTreeList(contain);
        if(menuList == null || menuList.isEmpty()){
            return new ArrayList<>();
        }
        List<JSONObject> results = new ArrayList<>();
        Map<String,List<JSONObject>> menuListMap = new HashMap<>();
        for(JSONObject menu : menuList){
            String level = menu.getString("menu_level");
            String menu_code = menu.getString("menu_code");
            String parent_code = menu.getString("parent_code");
            menu.put("open","false");
            menu.put("value",menu_code);
            if( "2".equals(level) && StringUtils.isNotEmpty(parent_code) ){
                List<JSONObject> menuList_map = menuListMap.get(parent_code);
                if( menuList_map == null || menuList_map.isEmpty()){
                    List<JSONObject> menuList_new = new ArrayList<>();
                    menuList_new.add(menu);
                    menuListMap.put(parent_code,menuList_new);
                } else {
                    menuList_map.add(menu);
                }
            }
            if( "1".equals(level) && StringUtils.isNotEmpty(menu_code) ){
                results.add(menu);
            }
        }
        if(results == null || results.isEmpty()){
            return new ArrayList<>();
        }
        for(JSONObject menu : results){
            String menu_code = menu.getString("menu_code");
            if(StringUtils.isEmpty(menu_code)){
                continue;
            }
            List<JSONObject> menuList_map = menuListMap.get(menu_code);
            if(menuList_map !=null && menuList_map.size()>0){
                menu.put("children",menuList_map);
            }
        }

        Map<String, Object> contain_System = new HashMap<>();
        contain_System.put("menuLevel","0");
        List<JSONObject> menuList_System = sysMenuMapper.getSysMenuTreeList(contain_System);
        if(menuList_System!=null && menuList_System.size()>0){
            JSONObject menu = menuList_System.get(0);
            menu.put("checked","true");
            results.add(menu);
        }
        return results;
    }


}
