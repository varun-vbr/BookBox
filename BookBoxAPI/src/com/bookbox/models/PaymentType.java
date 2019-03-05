package com.bookbox.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl_paymenttypes", schema="bookbox")
public class PaymentType {
	
	private int id;
	private String type;
	private int daysOfBilling;
	
	@Id
	@GeneratedValue
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(name="daysofbilling")
	public int getDaysOfBilling() {
		return daysOfBilling;
	}
	public void setDaysOfBilling(int daysOfBilling) {
		this.daysOfBilling = daysOfBilling;
	}
	

}
