package com.LI.Customer.model;

import java.util.List;

public interface CustomerDAO_interface {
	
	public void insert(CustomerVO customerVO);
	public void update(CustomerVO customerVO);
	public void delete(Integer customer_id);
	
	public CustomerVO findByPrimaryKey(Integer customer_id);
	
	public List<CustomerVO> getAll();

}


