package com.xyuan.product.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.xyuan.product.model.ProductService;
import com.xyuan.product.model.ProductVO;

@Controller
@RequestMapping("/product")
public class ProductBackController {

	@Autowired
	ProductService ProductSvc;
	
	
	@GetMapping("addProduct")
	public String addProduct(ModelMap model) {
		ProductVO productVO = new ProductVO();
		model.addAttribute("productVO", productVO);
		return "back-end/product/addProduct";
	}
	
	
	@PostMapping("insert")
	public String insert(@Valid ProductVO productVO, BindingResult result, ModelMap model,
			@RequestParam("productPic") MultipartFile[] parts) throws IOException{
		
		result = removeFieldError(productVO, result, "productPic");
		
		if(parts[0].isEmpty()) {
			model.addAttribute("errorMessage","請上傳商品圖片");
		}else {
			for(MultipartFile multipartFile : parts) {
				byte[] buf = multipartFile.getBytes();
				productVO.setProductPic(buf);
			}
		}
		if (result.hasErrors() || parts[0].isEmpty()) {
			return "back-end/product/addProduct";
		}
		
		ProductSvc.addProduct(productVO);
		
		List<ProductVO> list = ProductSvc.getAll();
		model.addAttribute("productListData", list);
		model.addAttribute("success", "新增成功");
		
		return "redirect:/product/listAllProduct";			
	}
	
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("productID") String productID, ModelMap model) {
	    ProductVO productVO = ProductSvc.getOneProduct(Integer.valueOf(productID));
	    model.addAttribute("productVO", productVO);
	    return "back-end/product/updateProductInput";
	}

	@PostMapping("update")
	public String update (@Valid ProductVO productVO, BindingResult result, ModelMap model,
	@RequestParam("productPic") MultipartFile[] parts) throws IOException {

	    result = removeFieldError(productVO, result, "productPic");

	    if (parts[0].isEmpty()) {
	        byte[] upFiles = ProductSvc.getOneProduct(productVO.getProductID()).getProductPic();
	        productVO.setProductPic(upFiles);
	    } else {
	        for (MultipartFile multipartFile : parts) {
	            byte[] upFiles = multipartFile.getBytes();
	            productVO.setProductPic(upFiles);
	        }
	    }
	    if (result.hasErrors()) {
	        return "back-end/product/updateProductInput";
	    }

	    ProductSvc.updateProduct(productVO);

	    model.addAttribute("success", "修改成功");
	    productVO = ProductSvc.getOneProduct(Integer.valueOf(productVO.getProductID()));
	    model.addAttribute("productVO", productVO);
	    return "back-end/product/listOneProduct";
	}


	public BindingResult removeFieldError(@Valid ProductVO productVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
				.collect(Collectors.toList());
		result = new BeanPropertyBindingResult(productVO, "productVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}

}
