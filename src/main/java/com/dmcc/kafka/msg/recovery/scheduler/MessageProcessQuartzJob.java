package com.dmcc.kafka.msg.recovery.scheduler;

import java.util.Iterator;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import com.dmcc.kafka.msg.recovery.config.EventMessageConstants;
import com.dmcc.kafka.msg.recovery.dto.EventMessageProcesserDTO;
import com.dmcc.kafka.msg.recovery.dto.LotMasterDTO;
import com.dmcc.kafka.msg.recovery.service.EventMessageProcesserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageProcessQuartzJob implements Job {
	@Autowired
	private EventMessageProcesserService eventMessageProcesserService;
	@Autowired
	private KafkaTemplate<String, LotMasterDTO> template;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("***Start MessageProcessQuartzJob***");

		List<EventMessageProcesserDTO> dtoList = eventMessageProcesserService
				.getEventMessagesByStatus(EventMessageConstants.EVENT_MESSAGE_STATUS_FAILED);
		System.out.println(":: Message List with status FAILED"+dtoList);
		try {
			for (Iterator iterator = dtoList.iterator(); iterator.hasNext();) {
				EventMessageProcesserDTO eventMessageProcesserDTO = (EventMessageProcesserDTO) iterator.next();
				LotMasterDTO lotMasterDTO = (LotMasterDTO) new ObjectMapper().readValue(eventMessageProcesserDTO.getMessage(), LotMasterDTO.class);
				System.out.println(":: Message Id from eventMessageProcesserDTO ::"+eventMessageProcesserDTO.getMessageId());
				lotMasterDTO.setEventMsgId(eventMessageProcesserDTO.getMessageId());
				System.out.println(":: MessageId from  lotMasterDTO"+lotMasterDTO.getEventMsgId());
				eventMessageProcesserService.markEventMessageWithCurrentStatus(EventMessageConstants.EVENT_MESSAGE_STATUS_ERROR, eventMessageProcesserDTO);
				template.send("OriginalTopic", lotMasterDTO);
			}
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {		
			e.printStackTrace();
		}
		System.out.println("***End MessageProcessQuartzJob***");

	}

}
