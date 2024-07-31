package com.tang.campaign.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("campaignService")
public class CampaignService {
	
	@Autowired
	CampaignRepository repository;
	
	
	public void addCampaign(CampaignVO campaignVO) {
		repository.save(campaignVO);
	}
	
	public void updateCampaign (CampaignVO campaignVO) {
		repository.save(campaignVO);
	}
	
	public void deleteCampaign(Integer campaignID) {
		if(repository.existsById(campaignID))
			repository.deleteByCampaignID(campaignID);
	}
	
	public CampaignVO getOneCampaign (Integer campaignID) {
		Optional<CampaignVO> optional = repository.findById(campaignID);
		return optional.orElse(null);
	}
	
	public List<CampaignVO> gatAll(){
		return repository.findAll();
	}
	
	//用不到複合查詢
//	public List<CampaignVO> getAll(Map<String, String[]> map){
//		return null;
//		return CompositeQuery_Campaign.getAllC(map);
//	}
}
