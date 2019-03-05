package com.bookbox.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="tbl_preferredcategories", schema="bookbox")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class PreferedCategory {
   private int id;
   private User user;
   private BookCategory category;
   
@Id
@GeneratedValue
@Column(name="id")  
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
@ManyToOne
@JoinColumn(name="userid")
@JsonManagedReference
public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}
@ManyToOne
@JoinColumn(name="categoryid")
public BookCategory getCategory() {
	return category;
}
public void setCategory(BookCategory category) {
	this.category = category;
}
   
}
