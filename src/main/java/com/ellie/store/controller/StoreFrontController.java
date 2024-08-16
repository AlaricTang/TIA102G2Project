package com.ellie.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ellie.store.model.StoreService;
import com.ellie.store.model.StoreVO;

@Controller
@RequestMapping("/store")
public class StoreFrontController {

    @Autowired
    StoreService storeService;

    // 顯示所有店家資訊頁面
    @GetMapping("/viewAllStores")
    public String viewAllStores(ModelMap modelMap) {
        List<StoreVO> stores = storeService.getAll();
        modelMap.addAttribute("stores", stores);
        return "back-end/store/viewAllStores"; // 返回前台店家列表頁面
    }

    // 顯示單個店家的詳細資訊
    @GetMapping("/viewOneStore")
    public String viewOneStore(@RequestParam("storeID") Integer storeID, ModelMap modelMap) {
        StoreVO storeVO = storeService.getOneStore(storeID);
        if (storeVO != null) {
            modelMap.addAttribute("storeVO", storeVO);
            return "back-end/store/viewOneStore"; // 返回顯示單個店家資訊頁面
        } else {
            return "redirect:/store/viewAllStores"; // 如果找不到該店家，回到店家列表頁面
        }
    }
}
