package com.leon.service.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.leon.druid.config.DataSource;
import com.leon.druid.config.DataSourceEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    @DataSource(DataSourceEnum.POST)
    @Transactional
    public List<JSONObject> getEnrollList() {
        return null;
    }

    @Override
    @DataSource(DataSourceEnum.GET)
    @Transactional
    public List<JSONObject> getEnrollList2() {
        return null;
    }

}
