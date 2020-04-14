package com.bookbox.services;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookbox.models.Book;
import com.bookbox.models.BookBoxConstants;
import com.bookbox.models.CurrentBook;
import com.bookbox.repositories.UserActionDao;

@Service
public class UserActionService {
	@Autowired
	private UserActionDao actionDao;
	
	public List<Book> getAllBooksInCategory(int categoryId){
		return actionDao.getAllBooksForCategory(categoryId);
	}
	
	public CurrentBook getCurrentBook(int bookId, int userId) {
		return actionDao.getCurrentBook(bookId, userId);
	}
	
	public boolean openBook(Map<String,Integer> currentRead){
		boolean success=actionDao.setCurrentBook(currentRead);
		if(success)
			success=actionDao.updateBookReadCount(currentRead.get("bookId"));
		return success;
	}
	
	public boolean recordReadingProgress(Map<String, Integer> currentRead) {
		return actionDao.setCurrentBook(currentRead);
	}
	
	public Map<String, Object> getAllPlaylistDetails(int userId, String userName){
		return actionDao.getAllPlaylistWithDetails(userId, userName);
		}
	
	public boolean addPlayList(String playListName, int userId) {
		return actionDao.createPlayList(playListName, userId);
	}
	
	public boolean deletePlaylist(int playlistId) {
        return actionDao.deletePlaylist(playlistId);		
	}
	
	public boolean addBookToPlaylist(int playlistId, int bookId) {
		return actionDao.addBookToPlaylist(playlistId, bookId);
	}
	
	public boolean removeBookFromPlaylist(int playlistId, int bookId) {
		return actionDao.removeBookFromPlaylist(playlistId, bookId);
	}
	
	public List<Book> searchAllBooks(String key, String type){
		if(type.equalsIgnoreCase("Title"))
			return actionDao.search(key, BookBoxConstants.SearchType.TITLE);
		else if(type.equalsIgnoreCase("Author"))
			return actionDao.search(key, BookBoxConstants.SearchType.AUTHOR);
		else if(type.equalsIgnoreCase("Publisher"))
			return actionDao.search(key, BookBoxConstants.SearchType.PUBLISHER);
		else if(type.equalsIgnoreCase("None"))
			return actionDao.search(key, BookBoxConstants.SearchType.NONE);
		else {
			return Collections.EMPTY_LIST;
		}
	}
	

}
