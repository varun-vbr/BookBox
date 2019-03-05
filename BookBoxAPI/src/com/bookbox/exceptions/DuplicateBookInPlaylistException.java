package com.bookbox.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN,
reason="Book already exists in playlist")
public class DuplicateBookInPlaylistException extends RuntimeException {
	private static final long serialVersionUID = 1L;

}
