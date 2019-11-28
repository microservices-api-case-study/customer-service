package com.retail.services.customerservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.services.customerservice.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
