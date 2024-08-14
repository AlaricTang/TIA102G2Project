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

	
}
