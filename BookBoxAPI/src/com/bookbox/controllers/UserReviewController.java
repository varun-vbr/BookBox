package com.bookbox.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bookbox.models.UserRating;
import com.bookbox.services.UserReviewService;

@Controller
@RequestMapping("/review")
@CrossOrigin(origins = {"http://127.0.0.1:8989","http://127.0.0.1","http://localhost", "http://localhost:8125"}, maxAge=4800)
public class UserReviewController {
	
	@Autowired
	private UserReviewService reviewService;
	private static final String SUCCESS="Success";
	private static final String FAILURE="Failure";
	
	@RequestMapping(value="/allReviews/{bookId}", method=RequestMethod.GET)
	public ResponseEntity<List<UserRating>> getRatingsForBook(@PathVariable("bookId")int bookId){
		List<UserRating> ratings=reviewService.getReviewsForaBook(bookId);
		return new ResponseEntity<List<UserRating>>(ratings,HttpStatus.OK);
	}
	
	@RequestMapping(value="/reviewCount/{bookId}", method=RequestMethod.GET)
	public ResponseEntity<Integer> getBookReviewCount(@PathVariable("bookId")int bookId){
		int count=reviewService.getReviewCount(bookId);
		return new ResponseEntity<Integer>(count,HttpStatus.OK);
	}
	
	@RequestMapping(value="/createReview", method=RequestMethod.POST)
	public ResponseEntity<String> addUserRating(@RequestBody Map<String,Object> review){
		boolean success=reviewService.createUserRating(review);
		if(success) 
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<String>(FAILURE, HttpStatus.BAD_REQUEST);
	}

}
