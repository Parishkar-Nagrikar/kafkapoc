package com.dmcc.kafka.msg.recovery.dto;

import java.math.BigDecimal;

/**
 * @author 1002410
 *
 */
public class LotMasterDTO implements Cloneable {

	private Integer lotId;

	private String lotStatus;

	private Integer lotVersion;

	private String lotCreationType;

	private String totalQuality;

	private BigDecimal totalQuantity;

	private Integer variety;

	private String EventMsgStatus;
	
	private String EventMsgId;

	public BigDecimal getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(BigDecimal totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public Integer getLotId() {
		return lotId;
	}

	public void setLotId(Integer lotId) {
		this.lotId = lotId;
	}

	public String getLotStatus() {
		return lotStatus;
	}

	public void setLotStatus(String lotStatus) {
		this.lotStatus = lotStatus;
	}

	public Integer getLotVersion() {
		return lotVersion;
	}

	public void setLotVersion(Integer lotVersion) {
		this.lotVersion = lotVersion;
	}

	public String getLotCreationType() {
		return lotCreationType;
	}

	public void setLotCreationType(String lotCreationType) {
		this.lotCreationType = lotCreationType;
	}

	public String getTotalQuality() {
		return totalQuality;
	}

	public void setTotalQuality(String totalQuality) {
		this.totalQuality = totalQuality;
	}

	public Integer getVariety() {
		return variety;
	}

	public void setVariety(Integer variety) {
		this.variety = variety;
	}

	@Override
	public LotMasterDTO clone() throws CloneNotSupportedException {
		// TODO Auto-generated constructor stub
		return (LotMasterDTO) super.clone();
	}

	public String getEventMsgStatus() {
		return EventMsgStatus;
	}

	public void setEventMsgStatus(String eventMsgStatus) {
		EventMsgStatus = eventMsgStatus;
	}

	public String getEventMsgId() {
		return EventMsgId;
	}

	public void setEventMsgId(String eventMsgId) {
		EventMsgId = eventMsgId;
	}

	@Override
	public String toString() {
		return "LotMasterDTO [lotId=" + lotId + ", lotStatus=" + lotStatus + ", lotVersion=" + lotVersion
				+ ", lotCreationType=" + lotCreationType + ", totalQuality=" + totalQuality + ", totalQuantity="
				+ totalQuantity + ", variety=" + variety + ", EventMsgStatus=" + EventMsgStatus + ", EventMsgId="
				+ EventMsgId + "]";
	}

	

}
