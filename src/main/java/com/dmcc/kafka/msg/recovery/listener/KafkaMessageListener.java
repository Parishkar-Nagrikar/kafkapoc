package com.dmcc.kafka.msg.recovery.listener;

import org.apache.kafka.common.protocol.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.dmcc.kafka.msg.recovery.dto.LotMasterDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class KafkaMessageListener {
		
	@Autowired
	private ObjectMapper objectMapper;
	private KafkaTemplate<String, LotMasterDTO> template;
	@KafkaListener(topics = "OriginalTopic", groupId = "KafkaDemo", containerFactory = "kafkaListenerContainerFactory")
	public void mainTopicListener(String meesage, Acknowledgment ackMode) throws Exception {
		LotMasterDTO lotMasterDTO = null;
		System.out.println(" \n ::before Processing the OriginalTopic :: \n");
		
		try {
			lotMasterDTO = objectMapper.readValue(meesage, LotMasterDTO.class);
			if(lotMasterDTO.getVariety() != null) {
				System.out.println("Processed OriginalTopic ::"+lotMasterDTO.toString());
			
			}else {
				
				throw new NullPointerException();
			}
			ackMode.acknowledge();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println(" \\n ::after Listening the Main Topic :: \n");
		
	}
	@KafkaListener(topics = "RecoveryTopic", groupId = "KafkaDemo", containerFactory = "kafkaRetryListenerContainerFactory")
	public void retryTopicListener(String meesage, Acknowledgment ackMode) throws Exception {
		LotMasterDTO lotMasterDTO = null;
		System.out.println(" \n ::before Listening the RecoveryTopic :: \n");
		try {
			lotMasterDTO = objectMapper.readValue(meesage, LotMasterDTO.class);
			
			System.out.println("RecoveryTopic Processed ::"+lotMasterDTO.toString());
			
			
			ackMode.acknowledge();
		} catch (JsonProcessingException e) {
						
			e.printStackTrace();
		}
		System.out.println(" \n ::After Listening the RecoveryTopic :: \n");
		
	}
}
