package com.dmcc.kafka.msg.recovery.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "EventMessageProcesser", schema = "conf_mgt")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class EventMessageProcesserDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;

	@Column(name = "MessageId")
	private String messageId;

	@Column(name = "FailureReason")
	private String failureReason;

	@Column(name = "ModuleName")
	private String moduleName;

	@Column(name = "Description")
	private String description;

	@Column(name = "Message")
	private String message;

	@Column(name = "Status")
	private String status;

	@Column(name = "RecordStatus")
	private String recordStatus;

	@Column(name = "CreatedBy")
	private String createdBy;

	@Column(name = "CreatedDateTime")
	private Date createdDateTime;

	@Column(name = "ModifiedBy")
	private String modifiedBy;

	@Column(name = "ModifiedDateTime")
	private Date modifiedDateTime;

	@Column(name = "NoofAttmepts")
	private String noofAttmepts;
	
	@Column(name = "TopicName")
	private String topicName;

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDateTime() {
		return modifiedDateTime;
	}

	public void setModifiedDateTime(Date modifiedDateTime) {
		this.modifiedDateTime = modifiedDateTime;
	}

	public String getNoofAttmepts() {
		return noofAttmepts;
	}

	public void setNoofAttmepts(String noofAttmepts) {
		this.noofAttmepts = noofAttmepts;
	}

	@Override
	public String toString() {
		return "EventMessageProcesserDTO [id=" + id + ", MessageId=" + messageId + ", failureReason=" + failureReason
				+ ", moduleName=" + moduleName + ", description=" + description + ", message=" + message + ", status="
				+ status + ", recordStatus=" + recordStatus + ", createdBy=" + createdBy + ", noofAttmepts="
				+ noofAttmepts + ", createdDateTime=" + createdDateTime + ", modifiedBy=" + modifiedBy
				+ ", modifiedDateTime=" + modifiedDateTime + "]";
	}

	public EventMessageProcesserDTO() {
		// Default Constructor
	}

	public EventMessageProcesserDTO(Long id, String messageId, String failureReason, String moduleName,
			String description, String message, String recordStatus, String createdBy, Date createdDateTime,
			String modifiedBy, Date modifiedDateTime, String noofAttmepts) {
		super();
		this.id = id;
		this.messageId = messageId;
		this.failureReason = failureReason;
		this.moduleName = moduleName;
		this.description = description;
		this.message = message;
		this.recordStatus = recordStatus;
		this.createdBy = createdBy;
		this.createdDateTime = createdDateTime;
		this.modifiedBy = modifiedBy;
		this.modifiedDateTime = modifiedDateTime;
		this.noofAttmepts = noofAttmepts;
	}

}
