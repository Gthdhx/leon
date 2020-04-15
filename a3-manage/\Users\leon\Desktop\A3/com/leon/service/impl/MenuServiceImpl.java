package com.leon.service.impl;

import org.springframework.stereotype.Service;

import com.leon.mapper.MenuMapper;
import com.leon.model.Menu;
import com.leon.service.IMenuService;
import com.baomidou.framework.service.impl.SuperServiceImpl;

/**
 *
 * Menu 表数据服务层接口实现类
 *
 */
@Service
public class MenuServiceImpl extends SuperServiceImpl<MenuMapper, Menu> implements IMenuService {


}