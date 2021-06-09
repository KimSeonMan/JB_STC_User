package com.neighbor.ServiceAPI.center.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value=HttpStatus.SERVICE_UNAVAILABLE, reason="Service Interruption")
public class ServiceUnavailableException extends HttpStatusException {
	public ServiceUnavailableException(String message) {
		super(message);
	}
}
