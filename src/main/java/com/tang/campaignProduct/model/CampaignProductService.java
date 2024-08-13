package com.tang.campaignProduct.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tang.campaign.model.CampaignVO;

@Service("campaignProductService")
public class CampaignProductService {

	@Autowired
	CampaignProductRepository repository;
	
	
	public void addCampaignProduct(CampaignProductVO campaignProductVO) {
		repository.save(campaignProductVO);
	}
	
	public void updateCampaignProducts(CampaignVO campaign, List<CampaignProductVO> newCampaignProductList) {
		
		//取全部原活動的商品
		List<CampaignProductVO> oriCampaignProducts = getAllByCampaignID(campaign.getCampaignID());
		
		//刪掉全部原活動的商品
		oriCampaignProducts.forEach(oldProduct -> deleteCampaignProduct(oldProduct.getCampaignProductID()));

		

		//放入 新活動商品
		newCampaignProductList.forEach(newProduct -> {
			newProduct.setCampaignVO(campaign);
			addCampaignProduct(newProduct);
		});
		

		
//		//原活動商品 刪掉update沒有的
//		List<CampaignProductVO> productsToDelete = oriCampaignProducts.stream()
//				.filter(oriProduct -> !newCampaignProductList.contains(oriProduct))
//				.collect(Collectors.toList());
//		productsToDelete.forEach(product -> deleteCampaignProduct(product.getCampaignProductID()));
		
		
//		//放入 原活動商品沒有的
//		
//		List<CampaignProductVO> productToAdd = new ArrayList<>();
//		
//		productToAdd.addAll(newCampaignProductList.stream()
//			    .filter(drink -> !oriCampaignProducts.contains(drink))
//			    .collect(Collectors.toList()));
//		
//		for(CampaignProductVO product : productToAdd) {
//			addCampaignProduct(product);
//		}
		
		
		
		
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
