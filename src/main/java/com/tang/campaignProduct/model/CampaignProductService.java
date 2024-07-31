package com.tang.campaignProduct.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("campaignProductService")
public class CampaignProductService {

	@Autowired
	CampaignProductRepository repository;
	
	
	public void addCampaignProduct(CampaignProductVO campaignProductVO) {
		repository.save(campaignProductVO);
	}
	
	public void updateCampaignProduct (CampaignProductVO campaignProductVO) {
		repository.save(campaignProductVO);
	}
	
	public void deleteCampaignProduct(Integer campaignProductID) {
		if(repository.existsById(campaignProductID))
			repository.deleteByCampaignProductID(campaignProductID);
	}
	
	public List<CampaignProductVO> getAllByCampaignID(Integer campaignID){
		return repository.getAllByCampaignID(campaignID);
	}
}
