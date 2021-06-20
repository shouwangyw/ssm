package com.yw.springmvc.example.service;

import com.yw.springmvc.example.dao.mapper.ItemMapper;
import com.yw.springmvc.example.dao.po.Item;
import com.yw.springmvc.example.dao.po.ItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品服务实现类
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;

    @Override
    public List<Item> queryItemList() {
        ItemExample example = new ItemExample();
        return itemMapper.selectByExample(example);
    }
}