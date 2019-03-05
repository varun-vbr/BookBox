package com.bookbox.controllers;

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

import com.bookbox.exceptions.IllegalLoginException;
import com.bookbox.exceptions.DuplicateUserCreationException;
import com.bookbox.exceptions.IllegalAccessException;
import com.bookbox.exceptions.UserNotFoundException;
import com.bookbox.models.UserInfo;
import com.bookbox.services.UserAccountService;

@Controller
@RequestMapping("/users")
public class UserAccountController {
	@Autowired
    private	UserAccountService userService;
	private static final String USER_NOT_FOUND="Username or email not found.";
	private static final String ILLEGAL_LOGIN="Invalid username or password.";
	private static final String ILLEGAL_ACCESS="Access Denied";
	private static final String SUCCESS="Success";
	private static final String FAILURE="Failure";
	private static final String USER_EXISTS="Username or email already exists";
	
    @RequestMapping(value="/login", method=RequestMethod.POST)
	public ResponseEntity performUserLogin(@RequestBody Map<String, String> loginDetails){
    	if(loginDetails!=null && loginDetails.containsKey("inputString") && loginDetails.containsKey("password")) {
		UserInfo userInfo=userService.loginForUserInfo(loginDetails.get("inputString"), loginDetails.get("password"));
		return new ResponseEntity<UserInfo>(userInfo,HttpStatus.OK);
    	}
    	else {
    		return new ResponseEntity<String>(FAILURE,HttpStatus.BAD_REQUEST);
    	}
	}
    @CrossOrigin(origins = "http://127.0.0.1:8989")
    @RequestMapping(value="/anonymous", method=RequestMethod.GET)
    public ResponseEntity<UserInfo> getDetailsForAnonymousLogin(){
    	UserInfo userInfo=userService.getUserInfoForAnonymousLogin();
    	return new ResponseEntity<UserInfo>(userInfo, HttpStatus.OK);
    }
    @RequestMapping(value="/create", method=RequestMethod.POST)
    public ResponseEntity<String> createNewUser(@RequestBody Map<String, Object> userDetails){
    	boolean success=userService.addUser(userDetails);
    	if(success) {
    		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    	}
    	else {
    		return new ResponseEntity<String>(FAILURE, HttpStatus.BAD_REQUEST);
    	}
    }
    @RequestMapping(value="/update", method=RequestMethod.PUT)
    public ResponseEntity<String> updateUserDetails(@RequestBody Map<String, Object> newDetails){
    	boolean success=userService.updateUser(newDetails);
    	if(success) {
    		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    	}
    	else {
    		return new ResponseEntity<String>(FAILURE, HttpStatus.BAD_REQUEST);
    	}
    }
    @ExceptionHandler(UserNotFoundException.class) 
    public ResponseEntity<String> handleUserNotFound(){
    	return new ResponseEntity<String>(USER_NOT_FOUND, HttpStatus.NOT_FOUND);
    	}
    @ExceptionHandler(IllegalLoginException.class)
    public ResponseEntity<String> handleIllegalLogin(){
    	return new ResponseEntity<String>(ILLEGAL_LOGIN, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<String> handleIllegalAccess(){
    	return new ResponseEntity<String>(ILLEGAL_ACCESS, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(DuplicateUserCreationException.class)
    public ResponseEntity<String> handleDuplicateUserCreation(){
    	return new ResponseEntity<String>(USER_EXISTS, HttpStatus.FORBIDDEN);
    }
    
    
}
