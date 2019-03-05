package com.bookbox.models;

import java.util.List;

public class UserInfo {
	public UserInfo() {}
	
	private User user;
	private List<CurrentBook> currentReads;
	private List<Book> readingSuggestions;
	private List<Book> popularBooks;
	private List<BookCategory> popularCategories;
	private List<Book> newBooks;
	private List<Book> trendingBooks;
	private List<BookCategory> allCategories;
	private List<Book> popularBooksFromPreferredCategories;
	public List<Book> getPopularBooksFromPreferredCategories() {
		return popularBooksFromPreferredCategories;
	}
	public void setPopularBooksFromPreferredCategories(List<Book> popularBooksFromPreferredCategories) {
		this.popularBooksFromPreferredCategories = popularBooksFromPreferredCategories;
	}
	public List<BookCategory> getAllCategories() {
		return allCategories;
	}
	public void setAllCategories(List<BookCategory> allCategories) {
		this.allCategories = allCategories;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<CurrentBook> getCurrentReads() {
		return currentReads;
	}
	public void setCurrentReads(List<CurrentBook> currentReads) {
		this.currentReads = currentReads;
	}
	public List<Book> getReadingSuggestions() {
		return readingSuggestions;
	}
	public void setReadingSuggestions(List<Book> readingSuggestions) {
		this.readingSuggestions = readingSuggestions;
	}
	public List<Book> getPopularBooks() {
		return popularBooks;
	}
	public void setPopularBooks(List<Book> popularBooks) {
		this.popularBooks = popularBooks;
	}
	public List<BookCategory> getPopularCategories() {
		return popularCategories;
	}
	public void setPopularCategories(List<BookCategory> popularCategories) {
		this.popularCategories = popularCategories;
	}
	public List<Book> getNewBooks() {
		return newBooks;
	}
	public void setNewBooks(List<Book> newBooks) {
		this.newBooks = newBooks;
	}
	public List<Book> getTrendingBooks() {
		return trendingBooks;
	}
	public void setTrendingBooks(List<Book> trendingBooks) {
		this.trendingBooks = trendingBooks;
	}

}
