package com.retail.services.customerservice.mq;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope
public class EventPublisherConfiguration {

	@Value("${rabbitmq.exchange-name}")
	String exchangeName;
	
	@Bean
	public Exchange eventExchange() {
		return new TopicExchange(exchangeName);
	}

}
