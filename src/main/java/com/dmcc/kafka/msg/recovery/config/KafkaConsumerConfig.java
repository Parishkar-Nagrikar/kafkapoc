package com.dmcc.kafka.msg.recovery.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.errors.TimeoutException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.listener.adapter.RetryingMessageListenerAdapter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import com.dmcc.kafka.msg.recovery.dto.EventMessageProcesserDTO;
import com.dmcc.kafka.msg.recovery.dto.LotMasterDTO;
import com.dmcc.kafka.msg.recovery.service.EventMessageProcesserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class KafkaConsumerConfig {
	@Autowired
	EventMessageProcesserService eventMessageProcesserService;

	@Bean
	public ConsumerFactory messageConsumerFactory() {

		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "KafkaDemo");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "30000");
		props.put(ConsumerConfig.RETRY_BACKOFF_MS_CONFIG, "5000");

		return new DefaultKafkaConsumerFactory<String, LotMasterDTO>(props);
	}

	@Bean(name = "kafkaListenerContainerFactory")
	ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
			ObjectProvider<ConsumerFactory<Object, Object>> kafkaConsumerFactory) {
		ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
		configurer.configure(factory, messageConsumerFactory());

		factory.setRetryTemplate(retryTemplate());

		factory.setRecoveryCallback((context -> {

			if (context.getLastThrowable().getCause() instanceof RecoverableDataAccessException
					|| context.getLastThrowable().getCause() instanceof NullPointerException) {
				// TODO
				/*
				 * Recovery mechanism Option 1 -> where you can put back on to the same topic or
				 * Persist in DB (will be picked up in the next process) using a Kafka producer
				 * Option 2 -> Can have logic to save in the DB later pick up by job scheduler
				 * and process accordingly
				 */
				try {

					String serviceCode = "DASSVC";

					ConsumerRecord consumerRecord = (ConsumerRecord) context
							.getAttribute(RetryingMessageListenerAdapter.CONTEXT_RECORD);
					System.out
							.println("in RecoveryCallback Processing message ...." + consumerRecord.value().toString());

					LotMasterDTO lotMasterDTO = (LotMasterDTO) new ObjectMapper().readValue(consumerRecord.value().toString(), LotMasterDTO.class);

					
					if (lotMasterDTO.getEventMsgId() != null) {
						System.out.println(":::: Duplicate Message with mssage Id::::"+lotMasterDTO.getEventMsgId());
					} else {
						System.out.println("::Message persistitng as it is first time ::" );
						EventMessageProcesserDTO eventMessageProcesserDTO = eventMessageProcesserService
								.prepareAndSaveEventMessage(serviceCode, consumerRecord.value().toString(),
										"LotMasterDTO", consumerRecord.topic());

					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {

				// TODO log or throw some custom exception that Error handler will take care of
				// ..
				System.out.println("in Recovery Callback Custom exception " + context.getLastThrowable().getMessage());
				throw new RuntimeException(context.getLastThrowable().getMessage());
			}

			return null;

		}));
		factory.setErrorHandler(((exception, data) -> {
			// TODO Handling the exception in such way where you can store the error msg for
			// further analysis and action accordingly
			System.out.println(" in Error Handling in process the record is {}::" + data.value());
		}));
		factory.getContainerProperties().setAckMode(AckMode.MANUAL_IMMEDIATE);
		return factory;
	}

	@Bean(name = "kafkaRetryListenerContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaRetryListenerContainerFactory(
			KafkaTemplate<String, Object> kafkaTemplate, ObjectMapper objectMapper) {
		ConcurrentKafkaListenerContainerFactory<String, String> retryFactory = new ConcurrentKafkaListenerContainerFactory<>();
		retryFactory.setMessageConverter(new StringJsonMessageConverter());
		retryFactory.setConsumerFactory(messageConsumerFactory());
		retryFactory.setRetryTemplate(retryTemplate());
		retryFactory.getContainerProperties().setAckMode(AckMode.MANUAL_IMMEDIATE);
		return retryFactory;

	}

	@Bean
	public RetryTemplate retryTemplate() {
		RetryTemplate retryTemplate = new RetryTemplate();

		FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
		fixedBackOffPolicy.setBackOffPeriod(3000);
		retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
		Map<Class<? extends Throwable>, Boolean> exceptionMap = new HashMap<>();
		exceptionMap.put(IllegalArgumentException.class, false);
		exceptionMap.put(TimeoutException.class, false);
		exceptionMap.put(NullPointerException.class, true);
		retryTemplate.setRetryPolicy(new SimpleRetryPolicy(3, exceptionMap, true));
		return retryTemplate;

	}

}
