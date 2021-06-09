package com.neighbor.ServiceAPI.center.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE, reason="Access Impossible")

public class NotAcceptableException extends HttpStatusException {
	public NotAcceptableException(String message) {
		super(message);
	}
}
