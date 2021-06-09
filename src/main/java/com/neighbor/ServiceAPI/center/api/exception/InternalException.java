package com.neighbor.ServiceAPI.center.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, reason="Server Exception")
public class InternalException extends HttpStatusException {
	public InternalException(String message) {
		super(message);
	}
}
