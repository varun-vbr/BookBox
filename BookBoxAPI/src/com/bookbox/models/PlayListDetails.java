package com.bookbox.models;

import java.util.List;

public class PlayListDetails {
private int playListId;
private String playListName;
private List<Book> books;

public int getPlayListId() {
	return playListId;
}
public void setPlayListId(int playListId) {
	this.playListId = playListId;
}
public String getPlayListName() {
	return playListName;
}
public void setPlayListName(String playListName) {
	this.playListName = playListName;
}
public List<Book> getBooks() {
	return books;
}
public void setBooks(List<Book> books) {
	this.books = books;
}

}
