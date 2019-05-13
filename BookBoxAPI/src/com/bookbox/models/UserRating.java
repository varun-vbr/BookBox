package com.bookbox.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tbl_userratings", schema="bookbox")
public class UserRating {
private int id;
private User user;
private Book book;
private int rating;
private String reviewComment;
private String reviewDate;

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
public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}
@ManyToOne
@JoinColumn(name="bookid")
public Book getBook() {
	return book;
}
public void setBook(Book book) {
	this.book = book;
}
@Column(name="rating")
public int getRating() {
	return rating;
}
public void setRating(int rating) {
	this.rating = rating;
}
@Column(name="reviewcomment")
public String getReviewComment() {
	return reviewComment;
}
public void setReviewComment(String reviewComment) {
	this.reviewComment = reviewComment;
}
@Column(name="reviewdate")
public String getReviewDate() {
	return reviewDate;
}
public void setReviewDate(String reviewDate) {
	this.reviewDate = reviewDate;
}
}
