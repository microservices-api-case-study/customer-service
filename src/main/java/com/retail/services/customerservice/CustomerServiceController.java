package com.retail.services.customerservice;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service1")
public class CustomerServiceController {
	
	private static final Logger log = LoggerFactory.getLogger(CustomerServiceController.class);
	
	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	EventPublisher eventPublisher;
	
	/**
	 * This operation returns the details of all the Customers
	 * @return List<Customer>
	 */
	@GetMapping("/customers")
	public List<Customer> getAllCustomers(){
		return customerRepository.findAll();
	}

	/**
	 * This operation persists the given Customer details in H2 DB
	 * and publishes the same through an event which subscribe by sales-order-service
	 * @param customer
	 */
	@GetMapping("/customer")
	public void addCustomer(@RequestBody Customer customer) {
		log.info("Adding a new customer...");
		customerRepository.save(customer);
		
		log.info("Publishing a message to notify sales-order-service...");
		eventPublisher.sendMessage(customer);
		
		log.info("Successfully added a new customer.");
	}
}
