package com.dmcc.kafka.msg.recovery.dto;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author 1002410
 *
 */
public class LotMasterDTO {

	private Integer lotId;

	private String lotStatus;

	private Integer lotVersion;

	private String lotCreationType;

	private String totalQuality;

	private BigDecimal totalQuantity;

	private Integer variety;

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
	public String toString() {
		return "LotMasterDTO [lotId=" + lotId + ", lotStatus=" + lotStatus + ", lotVersion=" + lotVersion
				+ ", lotCreationType=" + lotCreationType + ", totalQuality=" + totalQuality + ", variety=" + variety
				+ "]";
	}

}
