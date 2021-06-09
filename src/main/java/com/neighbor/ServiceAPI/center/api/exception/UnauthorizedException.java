package com.neighbor.ServiceAPI.center.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value=HttpStatus.UNAUTHORIZED, reason="Unauthorized")

public class UnauthorizedException extends HttpStatusException {
	public UnauthorizedException(String message) {
		super(message);
	}
}
