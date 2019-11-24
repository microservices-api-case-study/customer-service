package com.retail.services.customerservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service1")
public class CustomerServiceController {
	
	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	EventPublisher eventPublisher;
	
	@GetMapping("/customers")
	public List<Customer> getAllCustomers(){
		return customerRepository.findAll();
	}

	@GetMapping("/customer")
	public void sendMessage(@RequestBody Customer customer) {
		customerRepository.save(customer);
		eventPublisher.sendMessage(customer);
	}
}
