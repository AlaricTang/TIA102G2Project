package com.tang.campaign.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

import com.tang.campaign.model.CampaignService;
import com.tang.campaign.model.CampaignVO;
import com.tang.campaignProduct.model.CampaignProductService;
import com.tang.campaignProduct.model.CampaignProductVO;

@Controller
@RequestMapping("/campaign")
public class CampaignController {
	
	@Autowired
	CampaignService campaignSvc;
	
	@Autowired
	CampaignProductService campaignProductSvc;
	
	//=============== 查全部 =========
	@GetMapping("campaignList")
	public String campaignList(ModelMap model,HttpSession session) {
		List<CampaignVO> campaignList = campaignSvc.gatAll();
		model.addAttribute("campaignList",campaignList);
		
//		session.removeAttribute("campaignProductList");
		return "back-end/campaign/campaignList";
	}
	
	//=============== 跳新增頁 ===============
	@GetMapping("addCampaignPage")
	public String addCampaignPage(ModelMap model,HttpSession session) {
		
		CampaignVO campaignVO = new CampaignVO();
		model.addAttribute("campaignVO",campaignVO);
		
		//session 看有無已選擇商品
		@SuppressWarnings("unchecked")
		List<CampaignProductVO> campaignProductList =(List<CampaignProductVO>) session.getAttribute("campaignProductList");
		model.addAttribute("campaignProductList",campaignProductList);
		return "back-end/campaign/addCampaign";
	}

	
	//=============== 新增 ===============
	@PostMapping("insert")
	public String insert(
			@Valid CampaignVO campaignVO, BindingResult result, 
			@RequestParam("campaignPic") MultipartFile[] parts,
			ModelMap model,
			HttpSession session
			)throws IOException{
		

		//=========== 圖片 ===========
		result = removeFieldError(campaignVO, result, "campaignPic");
		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的圖片時
			model.addAttribute("errorMessage", "活動照片: 請上傳照片");
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] buf = multipartFile.getBytes();
				campaignVO.setCampaignPic(buf);
			}
		}		
		if (result.hasErrors() || parts[0].isEmpty()) {
			model.addAttribute("campaignVO",campaignVO);
			return "back-end/campaign/addCampaign";
		}
		//=========== 圖片 ===========

		
		//session 取出要加入的飲品
		@SuppressWarnings("unchecked")
		List<CampaignProductVO> campaignProductList = (List<CampaignProductVO>)session.getAttribute("campaignProductList"); 
		
		if(!campaignProductList.isEmpty()) {
			//存活動
			CampaignVO campaign = campaignSvc.addCampaign(campaignVO);
			
			//綁活動VO & 存活動飲品
			for(CampaignProductVO campaignProductVO:campaignProductList) {
				campaignProductVO.setCampaignVO(campaign);
				campaignProductSvc.addCampaignProduct(campaignProductVO);
			}			
		}else{
			model.addAttribute("errorMessage", "請選擇商品");
			model.addAttribute("campaignVO",campaignVO);
			return "back-end/campaign/addCampaign";
		}
		session.removeAttribute("campaignProductList");
		//返回活動列表
		List<CampaignVO> campaignList = campaignSvc.gatAll();
		model.addAttribute("campaignList", campaignList);
		return "redirect:/campaign/campaignList";
	}
		
	
	//=============== 跳更新頁 判斷有無session===============
	@PostMapping("updateCampaignPage")
	public String updateCampaignPage(
			@RequestParam("campaignID") String campaignID,ModelMap model,
			HttpSession session,HttpServletRequest request) {
		
		
		String refererUrl = request.getHeader("Referer");
		System.out.println(refererUrl);
		if (refererUrl.endsWith("/campaign/campaignList")) {
			session.removeAttribute("campaignProductList");
		}
		
		System.out.println(campaignID);
		CampaignVO campaignVO = campaignSvc.getOneCampaign(Integer.valueOf(campaignID));
		model.addAttribute("campaignVO", campaignVO);
		
		if( session.getAttribute("campaignProductList") == null ) {
			List<CampaignProductVO> campaignProductList = campaignProductSvc.getAllByCampaignID(Integer.valueOf(campaignID));
			session.setAttribute("campaignProductList", campaignProductList);			
		}
		//給商品選擇跳回來用的
		session.setAttribute("beUpdateCampaignID", campaignID);
		return "back-end/campaign/updateCampaign";
	}
	
	//=============== 更新 清session===============
	@PostMapping("update")
	public String update (
			@Valid CampaignVO campaignVO,BindingResult result,
			@RequestParam("campaignPic") MultipartFile[] parts,
			ModelMap model,
			HttpSession session
			) throws IOException {
		
		
		//=========== 圖片 ===========
		result = removeFieldError(campaignVO, result, "campaignPic");
		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的新圖片時，返回原圖
			byte[] upFiles = campaignSvc.getOneCampaign(campaignVO.getCampaignID()).getCampaignPic();
			campaignVO.setCampaignPic(upFiles);
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] upFiles = multipartFile.getBytes();
				campaignVO.setCampaignPic(upFiles);
			}
		}
		if (result.hasErrors()) {
			model.addAttribute("campaignVO",campaignVO);
			return "back-end/campaign/updateCampaign";
		}
		//=========== 圖片 ===========


		
		//取出要更新的 新的活動商品列
	    @SuppressWarnings("unchecked")
		List<CampaignProductVO> campaignProductList = (List<CampaignProductVO>) session.getAttribute("campaignProductList");

	    if(!campaignProductList.isEmpty()) {
			//更新活動
			CampaignVO campaign = campaignSvc.updateCampaign(campaignVO);
			//活動商品 資料更新
	    	campaignProductSvc.updateCampaignProducts(campaign, campaignProductList);	

	    }else {
			model.addAttribute("errorMessage", "請選擇商品");
			model.addAttribute("campaignVO",campaignVO);
	    	return "back-end/campaign/updateCampaign";
	    }
		
		return "redirect:/campaign/campaignList";
	}
	
	
	
	
	
	// 去除BindingResult中某個欄位的FieldError紀錄
	public BindingResult removeFieldError(CampaignVO campaignVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()	//從 result 中取得所有的 FieldError 物件(代表驗證失敗的欄位)，將錯誤列表轉換成一個串流 
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname)) //使用 filter 過濾不等於 removedFieldname 的欄位錯誤
				.collect(Collectors.toList());//collect 方法將過濾後的錯誤收集到一個新的列表 errorsListToKeep 中
		result = new BeanPropertyBindingResult(campaignVO, "campaignVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}
	
}
