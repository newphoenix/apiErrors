package com.example;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorHandler {
	
	@ExceptionHandler(NotFoundException.class)
	public ProblemDetail notFoundExceptionHandler(NotFoundException ex) {
		
		
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
				
		return problemDetail;
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ProblemDetail methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
		
		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
		
		Map<String,String> validationErrors = new HashMap<>();
		
		ex.getBindingResult()
		.getFieldErrors()
		.forEach(el-> validationErrors.put(el.getField(),el.getDefaultMessage()));
		
		problemDetail.setProperty("Validation errors",validationErrors);
		
		return problemDetail;
	}

}
