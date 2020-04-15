package com.leon.service.system;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * 用户表
 *
 * @author flyCloud
 * @email 
 * @date 2018-08-31 15:01:21
 */
public interface UserService {

    List<JSONObject> getEnrollList();

    List<JSONObject> getEnrollList2();

}

