// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

package com.LI.customer.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CustomerRepository extends JpaRepository<CustomerVO, Integer> {

}