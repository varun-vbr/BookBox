package com.bookbox.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl_plans", schema="bookbox")
public class Plan {
	private int planId;
	private String planName;
	private float chargePerDay;
	
	@Id
	@GeneratedValue
	@Column(name="planid")
	public int getPlanId() {
		return planId;
	}
	public void setPlanId(int planId) {
		this.planId = planId;
	}
	@Column(name="planname")
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	@Column(name="chargeperday")
	public float getChargePerDay() {
		return chargePerDay;
	}
	public void setChargePerDay(float chargePerDay) {
		this.chargePerDay = chargePerDay;
	}

}
