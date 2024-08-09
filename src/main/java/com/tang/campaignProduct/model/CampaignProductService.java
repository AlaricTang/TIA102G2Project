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
	
	public void updateCampaignProducts(Integer campaignID, List<CampaignProductVO> newCampaignProductList) {
		
		//原活動商品
		List<CampaignProductVO> oriCampaignProducts = getAllByCampaignID(campaignID);
		
		
		//原活動商品 刪掉update沒有的
		List<CampaignProductVO> productsToDelete = oriCampaignProducts.stream()
				.filter(oriProduct -> !newCampaignProductList.contains(oriProduct))
				.collect(Collectors.toList());
		productsToDelete.forEach(product -> deleteCampaignProduct(product.getCampaignProductID()));
		
		//放入 原活動商品沒有的
		List<CampaignProductVO> productToAdd = new ArrayList<>();
		
		productToAdd.addAll(newCampaignProductList.stream()
			    .filter(drink -> !oriCampaignProducts.contains(drink))
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
