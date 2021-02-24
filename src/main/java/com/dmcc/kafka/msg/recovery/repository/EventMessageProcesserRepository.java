package com.dmcc.kafka.msg.recovery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dmcc.kafka.msg.recovery.dto.EventMessageProcesserDTO;

@Repository
public interface EventMessageProcesserRepository extends JpaRepository<EventMessageProcesserDTO, Long> {

	@Query(value = " SELECT NEXT VALUE FOR conf_mgt.EventProcesserMessageId", nativeQuery = true)
	public Long getMessageIdSeq();

	public List<EventMessageProcesserDTO> findByStatus(String status);

	public List<EventMessageProcesserDTO> findByMessageIdAndStatus(String messageId, String status);

}
