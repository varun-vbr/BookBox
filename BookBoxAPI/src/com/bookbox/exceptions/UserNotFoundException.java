package com.bookbox.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND,
reason="User Not Found")
public class UserNotFoundException extends RuntimeException {
	
  private static final long serialVersionUID = 1L;

}
