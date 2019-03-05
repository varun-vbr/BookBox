package com.bookbox.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN,
reason="There are no books in this category")
public class NoBooksFoundException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;
	
	

}
