package com.mlestyan.validation.person.exception.global;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalRestExceptionHandler {
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<Object> handleWrongInput(HttpMessageNotReadableException exception) {
		exception.printStackTrace();
		return ResponseEntity.badRequest().body("Wrong request! Please use /complete-person path to use the application and use POST request. Also use yyyy.MM.dd format for dates.");
	}
}
