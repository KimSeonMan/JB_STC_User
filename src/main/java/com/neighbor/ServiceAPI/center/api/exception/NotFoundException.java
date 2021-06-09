package com.neighbor.ServiceAPI.center.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Not Found")

public class NotFoundException extends HttpStatusException {
	public NotFoundException(String message) {
		super(message);
	}
}
