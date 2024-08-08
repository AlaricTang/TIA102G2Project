package com.tang.campaignProduct.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("campaignProductService")
public class CampaignProductService {

	@Autowired
	CampaignProductRepository repository;
	
	
	public void addCampaignProduct(CampaignProductVO campaignProductVO) {
		repository.save(campaignProductVO);
	}
	
	public void updateCampaignProducts(Integer campaignID, List<CampaignProductVO> campaignDrinkList) {
		
		//原活動商品
		List<CampaignProductVO> existingCampaignProducts = getAllByCampaignID(campaignID);
		
		
		//原活動商品 刪掉update沒有的
		List<CampaignProductVO> productsToDelete = existingCampaignProducts.stream()
				.filter(existingProduct -> !campaignDrinkList.contains(existingProduct))
				.collect(Collectors.toList());
		productsToDelete.forEach(product -> deleteCampaignProduct(product.getCampaignID()));
		
		//放入 原活動商品沒有的
		List<CampaignProductVO> productToAdd = new ArrayList<>();
		
		productToAdd.addAll(campaignDrinkList.stream()
			    .filter(drink -> !existingCampaignProducts.contains(drink))
			    .collect(Collectors.toList()));
		
		for(CampaignProductVO product : productToAdd) {
			addCampaignProduct(product);
		}
		
		
		
		
	}
	
//	public void updateCampaignProduct (CampaignProductVO campaignProductVO) {
//		repository.save(campaignProductVO);
//	}
	
	public void deleteCampaignProduct(Integer campaignProductID) {
		if(repository.existsById(campaignProductID))
			repository.deleteByCampaignProductID(campaignProductID);
	}
	
	public List<CampaignProductVO> getAllByCampaignID(Integer campaignID){
		return repository.getAllByCampaignID(campaignID);
	}
}
