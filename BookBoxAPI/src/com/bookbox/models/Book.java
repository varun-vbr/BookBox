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
@Table(name="tbl_books", schema="bookbox")
public class Book {
		
    private int bookId;
	
	private String title;
	
	private String author;
	
	private String publisher;
	
	private String description;
	
	private BookCategory categoryId;
	
	private int averageRating;
	
	private String localImageUrl;
	
	private String localBookUrl;
	
	private String serverImageUrl;
			
	private String serverBookUrl;
		
	private Date uploadedDate;
	
	private long readCount;
	
	

	@Id
	@GeneratedValue
	@Column(name="bookid")
	public int getBookId() {
		return bookId;
	}
	
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	@Column(name="title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name="author")
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	@Column(name="publisher")
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	@Column(name="description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@ManyToOne
	@JoinColumn(name="catagoryid")
	public BookCategory getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(BookCategory categoryId) {
		this.categoryId = categoryId;
	}
	@Column(name="averagerating")
	public int getAverageRating() {
		return averageRating;
	}
	public void setAverageRating(int averageRating) {
		this.averageRating = averageRating;
	}
	@Column(name="localimageurl")
	public String getLocalImageUrl() {
		return localImageUrl;
	}
	public void setLocalImageUrl(String localImageUrl) {
		this.localImageUrl = localImageUrl;
	}
	@Column(name="localbookurl")
	public String getLocalBookUrl() {
		return localBookUrl;
	}
	public void setLocalBookUrl(String localBookUrl) {
		this.localBookUrl = localBookUrl;
	}
	@Column(name="serverimageurl")
	public String getServerImageUrl() {
		return serverImageUrl;
	}
	public void setServerImageUrl(String serverImageUrl) {
		this.serverImageUrl = serverImageUrl;
	}
	@Column(name="serverbookurl")
	public String getServerBookUrl() {
		return serverBookUrl;
	}
	public void setServerBookUrl(String serverBookUrl) {
		this.serverBookUrl = serverBookUrl;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getUploadedDate() {
		return uploadedDate;
	}
	public void setUploadedDate(Date uploadedDate) {
		this.uploadedDate = uploadedDate;
	}
	
	@Column(name="readcount")
	public long getReadCount() {
		return readCount;
	}

	public void setReadCount(long readCount) {
		this.readCount = readCount;
	}

	
}
