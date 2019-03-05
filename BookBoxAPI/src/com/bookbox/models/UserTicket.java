package com.bookbox.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tbl_usertickets", schema="bookbox")
public class UserTicket {
private int referenceNumber;
private String type;
private User user;
private String details;
@Id
@GeneratedValue
@Column(name="referencenumber")
public int getReferenceNumber() {
	return referenceNumber;
}
public void setReferenceNumber(int referenceNumber) {
	this.referenceNumber = referenceNumber;
}
@Column(name="type")
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
@ManyToOne
@JoinColumn(name="userid")
public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}
@Column(name="details")
public String getDetails() {
	return details;
}
public void setDetails(String details) {
	this.details = details;
}


}
