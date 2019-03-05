package com.bookbox.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl_bookcategories", schema="bookbox")
public class BookCategory {
    
    private int categoryId;
	private String categoryName;
	private String categoryBookPath;
	private String  categoryImagePath;
	
	@Id
	@GeneratedValue
	@Column(name="categoryid")
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	@Column(name="categoryname")
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	@Column(name="categorybookpath")
	public String getCategoryBookPath() {
		return categoryBookPath;
	}
	public void setCategoryBookPath(String categoryBookPath) {
		this.categoryBookPath = categoryBookPath;
	}
	@Column(name="categoryimagepath")
	public String getCategoryImagePath() {
		return categoryImagePath;
	}
	public void setCategoryImagePath(String categoryImagePath) {
		this.categoryImagePath = categoryImagePath;
	}
}
