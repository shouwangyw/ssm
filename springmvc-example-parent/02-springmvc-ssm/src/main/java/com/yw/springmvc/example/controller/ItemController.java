package com.yw.springmvc.example.controller;

import com.yw.springmvc.example.dao.po.Item;
import com.yw.springmvc.example.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品item控制器
 *
 * @author yangwei
 */
@Controller
@RequestMapping("item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("query")
    @ResponseBody
    public List<Item> queryItem() {
        return itemService.queryItemList();
    }
}