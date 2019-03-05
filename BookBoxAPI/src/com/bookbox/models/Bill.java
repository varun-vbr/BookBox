package com.bookbox.models;

import java.util.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="tbl_bills", schema="bookbox")
public class Bill {
private int billNumber;
private Date billedOn;
private User userId;
private String billingPeriod;
private float paymentAmount;
private Date dueDate;
private String paymentStatus;
private Date paymentDate;
private Boolean billDue;
private int daysOfBilling;


@Id
@GeneratedValue
@Column(name="billnumber")
public int getBillNumber() {
	return billNumber;
}
public void setBillNumber(int billNumber) {
	this.billNumber = billNumber;
}
@Temporal(TemporalType.TIMESTAMP)
public Date getBilledOn() {
	return billedOn;
}
public void setBilledOn(Date billedOn) {
	this.billedOn = billedOn;
}
@ManyToOne
@JoinColumn(name="userid")
public User getUserId() {
	return userId;
}
public void setUserId(User userId) {
	this.userId = userId;
}
@Column(name="billingperiod")
public String getBillingPeriod() {
	return billingPeriod;
}
public void setBillingPeriod(String billingPeriod) {
	this.billingPeriod = billingPeriod;
}
@Column(name="paymentamount")
public float getPaymentAmount() {
	return paymentAmount;
}
public void setPaymentAmount(float paymentAmount) {
	this.paymentAmount = paymentAmount;
}
@Temporal(TemporalType.TIMESTAMP)
public Date getDueDate() {
	return dueDate;
}
public void setDueDate(Date dueDate) {
	this.dueDate = dueDate;
}
@Column(name="paymentstatus")
public String getPaymentStatus() {
	return paymentStatus;
}
public void setPaymentStatus(String paymentStatus) {
	this.paymentStatus = paymentStatus;
}
@Temporal(TemporalType.TIMESTAMP)
public Date getPaymentDate() {
	return paymentDate;
}
public void setPaymentDate(Date paymentDate) {
	this.paymentDate = paymentDate;
}
@Column(name="billdue")
public Boolean getBillDue() {
	return billDue;
}
public void setBillDue(Boolean billDue) {
	this.billDue = billDue;
}
@Column(name="daysOfBilling")
public int getDaysOfBilling() {
	return daysOfBilling;
}
public void setDaysOfBilling(int daysOfBilling) {
	this.daysOfBilling = daysOfBilling;
}


}
