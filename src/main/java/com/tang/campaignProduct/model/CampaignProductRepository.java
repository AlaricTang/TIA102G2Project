package com.tang.campaignProduct.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CampaignProductRepository extends JpaRepository<CampaignProductVO, Integer>{
	
	@Transactional
	@Modifying
	@Query(value="delete from campaignProduct where campaignProductID = ?1", nativeQuery = true)
	void deleteByCampaignProductID(int campaignProductID);
	
	@Query(value="select * from campaignProduct where campaignID =?1", nativeQuery = true)
	List<CampaignProductVO> getAllByCampaignID(int campaignID);
}
