package com.tang.campaign.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tang.campaign.model.CampaignService;
import com.tang.campaign.model.CampaignVO;
import com.tang.campaignProduct.model.CampaignProductService;

@Controller
@RequestMapping("/campaign")
public class CampaignController {
	
	@Autowired
	CampaignService campaignSvc;
	
	@Autowired
	CampaignProductService campaignProductSvc;
	
	@GetMapping("campaignList")
	public String campaignList(ModelMap model) {
		List<CampaignVO> campaignList = campaignSvc.gatAll();
		model.addAttribute("campaignList",campaignList);
		return "back-end/campaign/campaignList";
	}
	
	@GetMapping("addCampaign")
	public String addCampaign(ModelMap model) {
		CampaignVO campaignVO = new CampaignVO();
		model.addAttribute("campaignVO",campaignVO);
		return "back-end/campaign/addCampaign";
	}
	
	@GetMapping("insertCampaign")
	public String insertCampaign(
			@Valid CampaignVO campaignVO, BindingResult result, 
			ModelMap model,
			@RequestParam("upFiles") MultipartFile[] parts)throws IOException{
		result = removeFieldError(campaignVO, result, "upFiles");
		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的圖片時
			model.addAttribute("errorMessage", "活動照片: 請上傳照片");
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] buf = multipartFile.getBytes();
				campaignVO.setCampaignPic(buf);
			}
		}
		if (result.hasErrors() || parts[0].isEmpty()) {
			return "back-end/campaign/addCampaign";
		}
		
		
		campaignSvc.addCampaign(campaignVO);
		
		
		List<CampaignVO> list = campaignSvc.gatAll();
		model.addAttribute("campaignListData", list);
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/campaign/campaignList"; // 新增成功後重導至IndexController_inSpringBoot.java的第58行@GetMapping("/emp/listAllEmp")
	}
		
	
	// 去除BindingResult中某個欄位的FieldError紀錄
	public BindingResult removeFieldError(CampaignVO campaignVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
				.collect(Collectors.toList());
		result = new BeanPropertyBindingResult(campaignVO, "campaignVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}
	
}
