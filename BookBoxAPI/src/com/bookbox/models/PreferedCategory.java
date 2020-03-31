package com.bookbox.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="tbl_preferredcategories", schema="bookbox")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
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
@ManyToOne(fetch=FetchType.EAGER)
@JoinColumn(name="userid")
@JsonManagedReference
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}
@ManyToOne(fetch=FetchType.EAGER)
@JoinColumn(name="categoryid")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public BookCategory getCategory() {
	return category;
}
public void setCategory(BookCategory category) {
	this.category = category;
}
   
}
