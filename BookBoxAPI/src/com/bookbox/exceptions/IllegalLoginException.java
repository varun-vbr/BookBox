package com.bookbox.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN,
reason="Wrong Password")
public class IllegalLoginException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;

}
