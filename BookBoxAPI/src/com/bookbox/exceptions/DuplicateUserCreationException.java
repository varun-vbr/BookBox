package com.bookbox.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN,
reason="Username already exists")
public class DuplicateUserCreationException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;

}
