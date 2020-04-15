package com.leon.controller;

import com.leon.service.system.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("对学生表CRUD")
@RestController
@RequestMapping("/student")
public class HelloController {

    @Autowired
    private UserService userService;



    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }

    @ApiOperation(value = "查询学生")
    @GetMapping("/list")
    public Object listAlla(){
        return  userService.getEnrollList();
    }

    @ApiOperation(value = "查询学生")
    @GetMapping("/list2")
    public Object listAlla2(){
        return  userService.getEnrollList2();
    }



}
