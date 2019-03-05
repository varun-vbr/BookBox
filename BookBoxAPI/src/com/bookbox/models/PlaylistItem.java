package com.bookbox.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tbl_playlistitems", schema="bookbox")
public class PlaylistItem {
private int id;
private Playlist playlist;
private Book book;

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
@JoinColumn(name="playlistid")
public Playlist getPlaylist() {
	return playlist;
}
public void setPlaylist(Playlist playlist) {
	this.playlist = playlist;
}
@ManyToOne
@JoinColumn(name="bookid")
public Book getBook() {
	return book;
}
public void setBook(Book book) {
	this.book = book;
}
  
}
