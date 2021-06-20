package com.yw.springmvc.example.service;

import com.yw.springmvc.example.dao.po.Item;

import java.util.List;

/**
 * 商品服务接口
 *
 * @author yangwei
 */
public interface ItemService {
    /**
     * 查询商品信息（全部）
     */
    List<Item> queryItemList();
}