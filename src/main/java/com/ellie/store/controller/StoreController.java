package com.ellie.store.controller;

import com.ellie.store.model.StoreVO;
import com.ellie.store.model.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/store")
public class StoreController {

    @Autowired
    StoreService storeService;

    @GetMapping("/select_page")
    public String selectPage(ModelMap model) {
        List<StoreVO> stores = storeService.getAll();
        model.addAttribute("storeVO", stores);
        return "back-end/store/select_page";
    }

    @GetMapping("/addStore")
    public String addStoreForm(ModelMap model) {
        model.addAttribute("storeVO", new StoreVO());
        return "back-end/store/addStore";
    }

    @PostMapping("/addStore")
    public String addStore(@ModelAttribute("storeVO") StoreVO storeVO, BindingResult result,
                           @RequestParam("storePic") MultipartFile[] parts) throws IOException {
        if (result.hasErrors()) {
            return "back-end/store/addStore";
        }

        if (!parts[0].isEmpty()) {
            storeVO.setStorePic(parts[0].getBytes());
        } else {
            storeVO.setStorePic(null); // 或者設置一個默認的圖片
        }

        storeService.addStore(storeVO);
        return "redirect:/store/select_page";
    }

    @GetMapping("/listAllStore")
    public String listAllStore(ModelMap model) {
        List<StoreVO> stores = storeService.getAll();
        model.addAttribute("storeVO", stores);
        return "back-end/store/listAllStore";
    }

    @GetMapping("/listOneStore/{storeID}")
    public String listOneStore(@PathVariable Integer storeID, ModelMap model) {
        StoreVO store = storeService.getOneStore(storeID);
        if (store == null) {
            return "redirect:/store/select_page";
        }
        model.addAttribute("storeVO", store);
        return "back-end/store/listOneStore";
    }

    @GetMapping("/updateStore/{storeID}")
    public String updateStoreForm(@PathVariable Integer storeID, ModelMap model) {
        StoreVO store = storeService.getOneStore(storeID);
        if (store == null) {
            return "redirect:/store/select_page";
        }
        model.addAttribute("storeVO", store);
        return "back-end/store/updateStore";
    }

    @PostMapping("/updateStore")
    public String updateStore(@ModelAttribute("storeVO") StoreVO storeVO, BindingResult result,
                              @RequestParam("storePic") MultipartFile[] parts) throws IOException {
        if (result.hasErrors()) {
            return "back-end/store/updateStore";
        }

        if (!parts[0].isEmpty()) {
            storeVO.setStorePic(parts[0].getBytes());
        } else {
            byte[] existingPic = storeService.getOneStore(storeVO.getStoreID()).getStorePic();
            storeVO.setStorePic(existingPic);
        }

        storeService.updateStore(storeVO);
        return "redirect:/store/listAllStore";
    }

    @GetMapping("/delete/{storeID}")
    public String deleteStore(@PathVariable Integer storeID) {
        storeService.deleteStore(storeID);
        return "redirect:/store/listAllStore";
    }
}
