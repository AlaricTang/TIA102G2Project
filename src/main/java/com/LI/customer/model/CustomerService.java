package com.LI.customer.model;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.LI.customer.model.CustomerVO;

@Service("customerService")
public class CustomerService {

	@Autowired
	CustomerRepository repository;

	public void addCustomer(CustomerVO customerVO) {
		repository.save(customerVO);
	}

//	客服表格應該不用更新功能
//	public void updateCustomer(CustomerVO customerVO) {
//		repository.save(customerVO);
//	}

//	客服表格應該不用刪除功能
//	public void deleteCustomer(Integer customerID) {
//		if (repository.existsById(customerID))
//			repository.deleteById(customerID);
//	}


	public CustomerVO getOneCustomer(Integer customerID) {
		Optional<CustomerVO> optional = repository.findById(customerID);
//		return optional.get();
		return optional.orElse(null);  
	}

//  
//	public List<CustomerVO> getAll() {
//		return repository.findAll();
//	}
	
//  客服表格應該不用複合查詢
//	public Set<CustomerVO> getCustomersByCustomerID(Integer customerID) {
//		return getOneCustomer(customerID).getCustomers();
//	}

}
