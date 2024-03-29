package com.retail.services.customerservice;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.retail.services.customerservice.model.Customer;
import com.retail.services.customerservice.mq.EventPublisher;
import com.retail.services.customerservice.repos.CustomerRepository;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/service1")
@RefreshScope
public class CustomerServiceController {

	private static final Logger log = LoggerFactory.getLogger(CustomerServiceController.class);

	@Value("${app.custom.message}")
	String message;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	EventPublisher eventPublisher;

	/**
	 * This operation returns the details of all the Customers
	 * 
	 * @return List<Customer>
	 */
	@GetMapping("/customers")
	public List<Customer> getAllCustomers() {
		log.info(message);
		return customerRepository.findAll();
	}

	/**
	 * This operation persists the given Customer details in H2 DB and publishes the
	 * same through an event which subscribe by sales-order-service
	 * 
	 * @param customer
	 */
	@PostMapping("/customer")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid request format") })
	public String addCustomer(@Valid @RequestBody Customer customer) {
		log.info("Adding a new customer...");

		customerRepository.save(customer);

		log.info("Publishing an event to notify sales-order-service...");

		eventPublisher.publishEvent(customer);

		log.info("Successfully added a new customer and published 'customer.created' event.");

		return "Added - " + customer.toString();
	}

	@GetMapping("/fault")
	@HystrixCommand(fallbackMethod = "fallbackOnRuntimeException")
	public String toShowfaultTolerance() {
		throw new RuntimeException();
	}

	public String fallbackOnRuntimeException() {
		return "Fallback method has been executed successfully.";
	}
}
