package com.dmcc.kafka.msg.recovery.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.dmcc.kafka.msg.recovery.dto.LotMasterDTO;


@Configuration
public class KafkaProducerConfig {
	
	@Bean
	public ProducerFactory messageProducerFactory(){
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,true);
		
		return new DefaultKafkaProducerFactory(props);
	}
	
	public KafkaTemplate<String, LotMasterDTO>  kafkaMsgTemplate(){
		
		return new KafkaTemplate(messageProducerFactory()); 
		
	}
	
	

}
