package com.ubiquisoft.evaluation.domain;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Part {

	private String inventoryId;

	private PartType type;

	private ConditionType condition;


	public boolean isInWorkingCondition() {
		switch (this.condition) {
			case NEW:
			case GOOD:
			case WORN:
				return true;
			default:
				return false;
		}
	}

	@Override
	public String toString() {
		return "Part{" +
				       "inventoryId='" + inventoryId + '\'' +
				       ", type=" + type +
				       ", condition=" + condition +
				       '}';
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters *///region
	/* --------------------------------------------------------------------------------------------------------------- */

	public String getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(String inventoryId) {
		this.inventoryId = inventoryId;
	}

	public PartType getType() {
		return type;
	}

	public void setType(PartType type) {
		this.type = type;
	}

	public ConditionType getCondition() {
		return condition;
	}

	public void setCondition(ConditionType condition) {
		this.condition = condition;
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters End *///endregion
	/* --------------------------------------------------------------------------------------------------------------- */

}
