package com.bookbox.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bookbox.exceptions.DuplicateBookInPlaylistException;
import com.bookbox.exceptions.NoBooksFoundException;
import com.bookbox.models.Book;
import com.bookbox.models.CurrentBook;
import com.bookbox.services.UserActionService;

@Controller
@RequestMapping("/actions")
@CrossOrigin(origins = {"http://127.0.0.1:8989","http://127.0.0.1","http://localhost", "http://localhost:8125"}, maxAge=4800)
public class UserActionController {
    @Autowired
	private UserActionService actionService;
    private static final String SUCCESS="Success";
    private static final String FAILURE="Failure";
    private static final String NO_BOOKS="There are no books in the category or the category doesnt exist.";
    private static final String INPUT_MISSING="Required data missing";
    private static final String DUPLICATE_BOOK_IN_PLAYLIST="Book already exists";
    
    @RequestMapping(value="/books/{categoryId}", method=RequestMethod.GET)
    public ResponseEntity<List<Book>> retrieveAllBooksFromCategory(@PathVariable("categoryId")int categoryId){
    	List<Book> books=actionService.getAllBooksInCategory(categoryId);
    	return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
    }
    @RequestMapping(value="currentBook/{bookId}/{userId}", method=RequestMethod.GET)
    public ResponseEntity<CurrentBook> getCurrentBook(@PathVariable("bookId")int bookId, @PathVariable("userId")int userId){
    	return new ResponseEntity<CurrentBook>(actionService.getCurrentBook(bookId, userId), HttpStatus.OK);
    }
    @RequestMapping(value="/openBook", method=RequestMethod.POST)
    public ResponseEntity<Map<String, String>> openBook(@RequestBody Map<String,Integer> currentRead){
    	boolean success=actionService.openBook(currentRead);
    	if(success) {
    		Map<String, String> response=new HashMap<String,String>();
    		response.put("msg",SUCCESS);
    		return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
    	}
    	else {
    		Map<String, String> response=new HashMap<String,String>();
    		response.put("errorMsg",FAILURE);
    		return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
    	}
    }
    
    @RequestMapping(value="/recordProgress", method=RequestMethod.POST)
    public ResponseEntity<Map<String, String>> recordReadingProgress(@RequestBody Map<String, Integer> currentRead){
    	boolean success=actionService.recordReadingProgress(currentRead);
    	if(success) {
    		Map<String, String> response=new HashMap<String,String>();
    		response.put("msg",SUCCESS);
    		return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
    	}
    	else {
    		Map<String, String> response=new HashMap<String,String>();
    		response.put("errorMsg",FAILURE);
    		return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
    	}
    }
    
    @RequestMapping(value="/playlists/allUserPlaylists/{userId}/{userName}", method=RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getUserPlaylists(@PathVariable("userId")int userId, @PathVariable("userName")String userName){
    	return new ResponseEntity<Map<String, Object>>(actionService.getAllPlaylistDetails(userId, userName), HttpStatus.OK);
    }
    
    @RequestMapping(value="/playlists/create", method=RequestMethod.POST)
    public ResponseEntity<Map<String, String>> createUserPlaylist(@RequestBody Map<String, Object> playlistDetails){
    	String playlistName=(String)playlistDetails.get("name");
    	int userId=(Integer)playlistDetails.get("userId");
    	if(playlistName!=null && userId != 0) {
    		boolean success=actionService.addPlayList(playlistName, userId);
    		if(success) {
        		Map<String, String> response=new HashMap<String,String>();
        		response.put("msg",SUCCESS);
        		return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
        	}
        	else {
        		Map<String, String> response=new HashMap<String,String>();
        		response.put("errorMsg",FAILURE);
        		return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
        	}
    	}
    	Map<String, String> response=new HashMap<String,String>();
		response.put("errorMsg",INPUT_MISSING);
    	return new ResponseEntity<Map<String, String>>(response,HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping(value="/playlists/delete", method=RequestMethod.POST)
    public ResponseEntity<String> deleteUserPlaylist(@RequestBody Map<String, Integer> playlistDetails){
    	int playlistId=(Integer)playlistDetails.get("playlistId");
    	if(playlistId != 0) {
    		if(actionService.deletePlaylist(playlistId))
    			return new ResponseEntity<String>(SUCCESS,HttpStatus.OK);
    		else
    			return new ResponseEntity<String>(FAILURE,HttpStatus.BAD_REQUEST);
    	}
    	return new ResponseEntity<String>(INPUT_MISSING,HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping(value="/playlists/books/add", method=RequestMethod.POST)
    public ResponseEntity<Map<String, String>> addBookToPlaylist(@RequestBody Map<String, Integer> details){
    	int playlistId=(Integer)details.get("playlistId");
    	int bookId=(Integer)details.get("bookId");
    	if(playlistId != 0 && bookId != 0) {
    		boolean success=actionService.addBookToPlaylist(playlistId, bookId);
    		if(success) {
        		Map<String, String> response=new HashMap<String,String>();
        		response.put("msg",SUCCESS);
        		return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
        	}
        	else {
        		Map<String, String> response=new HashMap<String,String>();
        		response.put("errorMsg",FAILURE);
        		return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
        	}
    	}
    	Map<String, String> response=new HashMap<String,String>();
		response.put("errorMsg",INPUT_MISSING);
    	return new ResponseEntity<Map<String, String>>(response,HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping(value="/playlists/books/remove", method=RequestMethod.POST)
    public ResponseEntity<Map<String, String>> removeBookFromPlaylist(@RequestBody Map<String, Integer> details){
    	int playlistId=(Integer)details.get("playlistId");
    	int bookId=(Integer)details.get("bookId");
    	if(playlistId != 0 && bookId != 0) {
    		boolean success=actionService.removeBookFromPlaylist(playlistId, bookId);
    		if(success) {
        		Map<String, String> response=new HashMap<String,String>();
        		response.put("msg",SUCCESS);
        		return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
        	}
        	else {
        		Map<String, String> response=new HashMap<String,String>();
        		response.put("errorMsg",FAILURE);
        		return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
        	}
    	}
    	Map<String, String> response=new HashMap<String,String>();
		response.put("errorMsg",INPUT_MISSING);
    	return new ResponseEntity<Map<String, String>>(response,HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping(value="/search/{key}/{type}", method=RequestMethod.GET)
    public ResponseEntity<List<Book>> searchAllBooks(@PathVariable("key") String key, @PathVariable("type") String searchType){
    	return new ResponseEntity<List<Book>>(actionService.searchAllBooks(key, searchType), HttpStatus.OK);
    }
    
    @ExceptionHandler(NoBooksFoundException.class) 
    public ResponseEntity<String> handleUserNotFound(){
    	return new ResponseEntity<String>(NO_BOOKS, HttpStatus.BAD_REQUEST);
    	}
    
    @ExceptionHandler(DuplicateBookInPlaylistException.class) 
    public ResponseEntity<String> handleDuplicateBooksInPlaylist(){
    	return new ResponseEntity<String>(DUPLICATE_BOOK_IN_PLAYLIST, HttpStatus.BAD_REQUEST);
    	}
    
    
   }
