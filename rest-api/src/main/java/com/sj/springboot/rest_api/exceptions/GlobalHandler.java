/**
 * 
 */
package com.sj.springboot.rest_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author sange
 *
 */
@ControllerAdvice
public class GlobalHandler {
	
	@ExceptionHandler(BookNotFoundException.class)
	public ResponseEntity<String> bookDoesNotExist(BookNotFoundException e) {
	  return	ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		
	}
	
	
	

}
