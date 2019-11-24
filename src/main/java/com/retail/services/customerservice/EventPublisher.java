package com.retail.services.customerservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {
	
	private final Logger log = LoggerFactory.getLogger(EventPublisher.class);

	private final RabbitTemplate rabbitTemplate;

	public EventPublisher(ConnectionFactory connectionFactory) {
		this.rabbitTemplate = new RabbitTemplate(connectionFactory);
		this.rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
	}
	
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
	    return new Jackson2JsonMessageConverter();
	}

	public void sendMessage(Customer customerDetails) {
		rabbitTemplate.convertAndSend("CustomerCreated", customerDetails);
		log.info("Message sent from Customer Service => "+ customerDetails.toString());
	}
}
