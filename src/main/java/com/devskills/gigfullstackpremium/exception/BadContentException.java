package com.devskills.gigfullstackpremium.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadContentException extends RuntimeException {
	
	private static final long serialVersionUID = -4475530750611753362L;

	public BadContentException() {
	}

	public BadContentException(String message) {
		super(message);
	}

	public BadContentException(Throwable cause) {
		super(cause);
	}

	public BadContentException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadContentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
