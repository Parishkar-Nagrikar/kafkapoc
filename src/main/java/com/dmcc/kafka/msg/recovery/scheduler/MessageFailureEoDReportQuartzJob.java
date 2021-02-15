package com.dmcc.kafka.msg.recovery.scheduler;

import java.util.Iterator;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import com.dmcc.kafka.msg.recovery.dto.EventMessageProcesserDTO;
import com.dmcc.kafka.msg.recovery.dto.LotMasterDTO;
import com.dmcc.kafka.msg.recovery.service.MessagepersistanceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageFailureEoDReportQuartzJob implements Job {
	@Autowired
	private MessagepersistanceService messagepersistanceService;
	@Autowired
	private KafkaTemplate<String, LotMasterDTO> template;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("***Start messagepersistanceService***");
		//List<EventMessageProcesserDTO> dtoList = messagepersistanceService.findAll();
		
		List<EventMessageProcesserDTO> dtoList = messagepersistanceService.findByStatus("NEW");
		 
		try {
		for (Iterator iterator = dtoList.iterator(); iterator.hasNext();) {
			
			EventMessageProcesserDTO eventMessageProcesserDTO = (EventMessageProcesserDTO) iterator.next();
			System.out.println(" Message :: from eventMessageProcesserDTO ::"+eventMessageProcesserDTO.getMessage());
			LotMasterDTO lotMasterDTO = (LotMasterDTO) new ObjectMapper().readValue(eventMessageProcesserDTO.getMessage(), LotMasterDTO.class);
			//send notification
			sendMessageFailureNotification(eventMessageProcesserDTO);
			template.send("OriginalTopic",lotMasterDTO)	;
			//mark as notified
			
			}
		}catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("***End messagepersistanceService***");
	
	}
	public void markMessageAsNotifed(EventMessageProcesserDTO eventMessageProcesserDTO) {
		System.out.println("markMessageAsNotifed :::"+eventMessageProcesserDTO.getStatus());
		if(eventMessageProcesserDTO.getStatus() != null && "FAILED".equalsIgnoreCase(eventMessageProcesserDTO.getStatus())) {
			eventMessageProcesserDTO.setStatus("NOTIFIED"); 
			messagepersistanceService.save(eventMessageProcesserDTO);
		}
	}
	public void sendMessageFailureNotification(EventMessageProcesserDTO eventMessageProcesserDTO) {
		//
		//send notification
	}
}
