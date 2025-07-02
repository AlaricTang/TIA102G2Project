// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

package com.repository;

import com.entity.CustomerVO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerVO, Integer> {

}