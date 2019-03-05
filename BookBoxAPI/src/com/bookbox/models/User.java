package com.bookbox.models;

import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="tbl_users", schema="bookbox")
public class User {
	private int userId;
	private String name;
	private String username;
	private String password;
	private String email;
	private String phone;
	private String address;
	private String cardNumber;
	private String pinCode;
	private Boolean enabled;
	private Plan plan;
	private PaymentType paymenttype;
	private Boolean autoDeduction;
	private Date userSince;
	private Date planSince;
	private String authority;
	private List<PreferedCategory> preferredCategories;
	
	
	
	
	@Id
	@GeneratedValue
	@Column(name="userid")
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="username")
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Column(name="password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name="email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name="phone")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(name="address")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name="cardnumber")
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	@Column(name="pincode")
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	@Column(name="enabled")
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	@ManyToOne
	@JoinColumn(name="plan")
	public Plan getPlan() {
		return plan;
	}
	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	@ManyToOne
	@JoinColumn(name="paymenttype")
	public PaymentType getPaymenttype() {
		return paymenttype;
	}
	public void setPaymenttype(PaymentType paymenttype) {
		this.paymenttype = paymenttype;
	}
	@Column(name="autodeduction")
	public Boolean getAutoDeduction() {
		return autoDeduction;
	}
	public void setAutoDeduction(Boolean autoDeduction) {
		this.autoDeduction = autoDeduction;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getUserSince() {
		return userSince;
	}
	public void setUserSince(Date userSince) {
		this.userSince = userSince;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getPlanSince() {
		return planSince;
	}
	public void setPlanSince(Date planSince) {
		this.planSince = planSince;
	}
	@Column(name="authority")
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	@OneToMany(mappedBy="user", targetEntity=PreferedCategory.class, fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JsonBackReference
	public List<PreferedCategory> getPreferredCategories() {
		return preferredCategories;
	}
	public void setPreferredCategories(List<PreferedCategory> preferredCategories) {
		this.preferredCategories = preferredCategories;
	}
		

}
