package com.seam.messages.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.seam.messages.common.MessageConstant;
import com.seam.messages.pojo.SeamError;

@RestControllerAdvice
public class SeamGlobalExceptionHandler {

	@ExceptionHandler(SeamException.class)
	public ResponseEntity<SeamError> handleException(SeamException ex) {
		SeamError error = new SeamError();
		error.setErrorMessage(ex.getMessage());
		error.setSuccess(MessageConstant.FAILURE_STATUS);
		return new ResponseEntity<SeamError>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
