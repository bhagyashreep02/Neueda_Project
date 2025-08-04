/**
 * 
 */
package com.sj.springboot.rest_api.exceptions;

import java.util.NoSuchElementException;

/**
 * @author sange
 *
 */
public class BookNotFoundException extends NoSuchElementException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BookNotFoundException() {
		super("Book does not exist ");
		// TODO Auto-generated constructor stub
	}
	
	  
	
	

}
