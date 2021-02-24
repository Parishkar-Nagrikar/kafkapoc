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
		
		try {
			lotMasterDTO = objectMapper.readValue(meesage, LotMasterDTO.class);
			if (lotMasterDTO.getVariety() != null) {
				System.out.println("Processed OriginalTopic ::" + lotMasterDTO.toString());

			} else {

				throw new NullPointerException();
			}
			ackMode.acknowledge();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		

	}

}