package com.dmcc.kafka.msg.recovery.service;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dmcc.kafka.msg.recovery.dto.EventMessageProcesserDTO;
import com.dmcc.kafka.msg.recovery.dto.LotMasterDTO;

public interface MessagepersistanceService extends JpaRepository<EventMessageProcesserDTO, Long> {
	
	@Query(value = " SELECT NEXT VALUE FOR conf_mgt.EventProcesserMessageId", nativeQuery = true)
	public Long getMessageIdSeq();
	
	public List<LotMasterDTO> findAllByIdIn(List<Long> ids);
	
	public List<EventMessageProcesserDTO> findByStatus(String status);
	

}
