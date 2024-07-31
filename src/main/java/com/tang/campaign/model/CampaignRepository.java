package com.tang.campaign.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CampaignRepository extends JpaRepository<CampaignVO, Integer>{

	@Transactional
	@Modifying
	@Query(value="delete from campaign where campaignID = ?1", nativeQuery = true)
	void deleteByCampaignID(int campaignID);
}
