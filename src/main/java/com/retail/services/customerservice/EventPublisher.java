package com.retail.services.customerservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {

	private final Logger log = LoggerFactory.getLogger(EventPublisher.class);

	private final RabbitTemplate rabbitTemplate;

	@Autowired
	Exchange exchange;

	public EventPublisher(ConnectionFactory connectionFactory) {
		this.rabbitTemplate = new RabbitTemplate(connectionFactory);
		this.rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
	}

	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	public void publishEvent(Customer customerDetails) {
		String routingKey = "customer.created";
		rabbitTemplate.convertAndSend(exchange.getName(), routingKey, customerDetails);
		log.info("Message sent from Customer Service => " + customerDetails.toString());
	}
}
