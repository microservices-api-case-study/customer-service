package com.retail.services.customerservice.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import com.retail.services.customerservice.model.Customer;

@Component
@RefreshScope
public class EventPublisher {
	
	@Value("${rabbitmq.routing-key}")
	String routingKey;

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
		rabbitTemplate.convertAndSend(exchange.getName(), routingKey, customerDetails);
		log.info("Message sent from Customer Service => " + customerDetails.toString());
	}
}
