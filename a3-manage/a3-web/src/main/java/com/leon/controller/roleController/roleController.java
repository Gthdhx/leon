package com.leon.controller.roleController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/role")
public class roleController {

    /**
     * 登陆页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/gotoRoleListPage")
    public ModelAndView gotoLoginPage(ModelAndView model) {
        model.setViewName("role/roleList");
        return model;
    }


}
