package com.leon.controller.menuController;

import com.alibaba.fastjson.JSONObject;
import com.leon.service.system.SysMenuService;
import com.leon.utils.LayUiListUtil;
import com.leon.utils.ResultUtil;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class menuController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 登陆页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/gotoMenuListPage")
    public ModelAndView gotoMenuListPage(ModelAndView model) {
        model.setViewName("menu/menuList");
        return model;
    }

    @GetMapping("/getMenuList")
    public LayUiListUtil getMenuList(){
        Map<String,Object> contain = new HashMap<>();
        return  sysMenuService.getMenuList(contain);
    }

    @RequestMapping(value = "/gotoMenuInsertPage")
    public ModelAndView gotoMenuInsertPage(ModelAndView model) {
        model.setViewName("menu/menuInsert");
        return model;
    }

    @PostMapping(value = "/insertMenu")
    public ResultUtil insertMenu(@RequestBody JSONObject person) {

        if(person == null){
            return ResultUtil.error();
        }
        return sysMenuService.insertMenu(person);
    }

    @RequestMapping(value = "/gotoMenuUpdatePage")
    public ModelAndView gotoMenuUpdatePage(ModelAndView model,@RequestParam(name = "id") String id) {

        if(StringUtil.isNotBlank(id)){
            Map<String,Object> contain = new HashMap<>();
            contain.put("id",id);
            JSONObject menuData = sysMenuService.getMenuByMap(contain);
            if(menuData!=null){
                model.addObject("menuData",menuData);
            }
        }
        model.setViewName("menu/menuUpdate");
        return model;
    }

    @GetMapping("/getSysMenuTreeList")
    public List<JSONObject> getSysMenuTreeList(){
        Map<String,Object> contain = new HashMap<>();
        return  sysMenuService.getSysMenuTreeList(contain);
    }

}
