package com.retail.services.customerservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.retail.services.customerservice.model.Customer;

@ActiveProfiles("junit")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CustomerServiceApplicationTests {

	private static String URL_PREFIX = "http://localhost:";

	@LocalServerPort
	private int port;

	@Autowired
	TestRestTemplate testRestTemplate;

	@Test
	void testGetAllCustomers() {

		ResponseEntity<List<Customer>> response = testRestTemplate.exchange(URL_PREFIX + port + "/service1/customers",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Customer>>() {
				});
		List<Customer> customers = response.getBody();
		assertTrue(customers.size() > 0);
		assertEquals("John", customers.get(0).getFirstName());

	}

	@Test
	void testAddCustomer() {

		Customer customer = new Customer();
		customer.setEmail("abc@def.com");
		customer.setFirstName("John");
		customer.setLastName("Smith");
		ResponseEntity<String> response = testRestTemplate.postForEntity(URL_PREFIX + port + "/service1/customer",
				customer, String.class);
		String responseMessage = response.getBody();
		assertTrue(responseMessage.startsWith("Added"));

	}

	@Test
	void testToShowfaultTolerance() {

		ResponseEntity<String> response = testRestTemplate.getForEntity(URL_PREFIX + port + "/service1/fault",
				String.class);
		String responseMessage = response.getBody();
		assertEquals("Fallback method has been executed successfully.", responseMessage);

	}

}
