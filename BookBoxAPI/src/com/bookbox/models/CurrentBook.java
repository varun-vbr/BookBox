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
@Table(name="tbl_currentbooks", schema="bookbox")
public class CurrentBook {
private int id;
private Book book;
private User user;
private int currentPage;
private int numberOfPages;
private int percentageCompleted;
private Date lastRead;

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
@JoinColumn(name="bookid")
public Book getBook() {
	return book;
}
public void setBook(Book book) {
	this.book = book;
}
@ManyToOne
@JoinColumn(name="userid")
public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}
@Column(name="currentpage")
public int getCurrentPage() {
	return currentPage;
}
public void setCurrentPage(int currentPage) {
	this.currentPage = currentPage;
}
@Column(name="numberofpages")
public int getNumberOfPages() {
	return numberOfPages;
}
public void setNumberOfPages(int numberOfPages) {
	this.numberOfPages = numberOfPages;
}
@Column(name="percentagecompleted")
public int getPercentageCompleted() {
	return percentageCompleted;
}
public void setPercentageCompleted(int percentageCompleted) {
	this.percentageCompleted = percentageCompleted;
}
@Temporal(TemporalType.TIMESTAMP)
public Date getLastRead() {
	return lastRead;
}
public void setLastRead(Date lastRead) {
	this.lastRead = lastRead;
}
}
