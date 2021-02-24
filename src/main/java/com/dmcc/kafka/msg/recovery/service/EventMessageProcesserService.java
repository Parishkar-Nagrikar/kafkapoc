package com.dmcc.kafka.msg.recovery.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dmcc.kafka.msg.recovery.config.EventMessageConstants;
import com.dmcc.kafka.msg.recovery.dto.EventMessageProcesserDTO;
import com.dmcc.kafka.msg.recovery.dto.LotMasterDTO;
import com.dmcc.kafka.msg.recovery.repository.EventMessageProcesserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EventMessageProcesserService {

	@Autowired
	EventMessageProcesserRepository eventMessageProcesserRepository;

	public EventMessageProcesserDTO prepareAndSaveEventMessage(String serviceCode, String eventMessage,
			String moduleName, String topicName) {
		EventMessageProcesserDTO eventMessageProcesserDTO = new EventMessageProcesserDTO();
		eventMessageProcesserDTO
				.setMessageId(eventMessageProcesserRepository.getMessageIdSeq().toString() + serviceCode);
		eventMessageProcesserDTO.setFailureReason("DB Lock");
		eventMessageProcesserDTO.setDescription("Failed due to DB Lock");
		eventMessageProcesserDTO.setMessage(eventMessage);
		eventMessageProcesserDTO.setStatus("FAILED");
		eventMessageProcesserDTO.setCreatedBy("Author");
		eventMessageProcesserDTO.setModifiedBy("Author");
		eventMessageProcesserDTO.setCreatedDateTime(new Date());
		eventMessageProcesserDTO.setModifiedDateTime(new Date());
		eventMessageProcesserDTO.setModuleName(moduleName);
		eventMessageProcesserDTO.setTopicName(topicName);
		return eventMessageProcesserRepository.save(eventMessageProcesserDTO);
	}

	public List<EventMessageProcesserDTO> getEventMessagesByStatus(String status) {
			return eventMessageProcesserRepository.findByStatus(status);
	}

	public EventMessageProcesserDTO markEventMessageWithCurrentStatus(String status,
			EventMessageProcesserDTO eventMessageProcesserDTO) {
		EventMessageProcesserDTO retunEventMessageProcesserObj = null;
		if (eventMessageProcesserDTO.getStatus() != null
				&& !(status.equalsIgnoreCase(eventMessageProcesserDTO.getStatus()))) {
			eventMessageProcesserDTO.setStatus(status);
			retunEventMessageProcesserObj = eventMessageProcesserRepository.saveAndFlush(eventMessageProcesserDTO);

		}
		return retunEventMessageProcesserObj;
	}

	public List<EventMessageProcesserDTO> getEventMessagesByMessageIsAndStatus(String messageId, String status) {
		return eventMessageProcesserRepository.findByMessageIdAndStatus(messageId, status);
	}

	public void sendEoDMessageFailureNotification() {

		List<EventMessageProcesserDTO> evntmsgproList = getEventMessagesByStatus(
				EventMessageConstants.EVENT_MESSAGE_STATUS_ERROR);

		/*
		 * TODO initiation of the call to send notification with list of messages per
		 * service in a tabular format
		 * notificationService.sendNotificationForEventMessageProcessFailure(
		 * evntmsgproList)
		 */

		for (Iterator<EventMessageProcesserDTO> itr = evntmsgproList.iterator(); itr.hasNext();) {
			EventMessageProcesserDTO eventMessageProcesserDTO = (EventMessageProcesserDTO) itr.next();
			LotMasterDTO newLotmst = new LotMasterDTO();
			try {
				System.out.println(" markEventMessageWithCurrentStatus ===>" + newLotmst);
				newLotmst = (LotMasterDTO) new ObjectMapper().readValue(eventMessageProcesserDTO.getMessage(),
						LotMasterDTO.class);
				eventMessageProcesserDTO = markEventMessageWithCurrentStatus(
						EventMessageConstants.EVENT_MESSAGE_STATUS_NOTIFIED, eventMessageProcesserDTO);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
