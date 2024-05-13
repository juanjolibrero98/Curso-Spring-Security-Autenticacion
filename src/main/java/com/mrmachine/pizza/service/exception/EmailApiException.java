package com.mrmachine.pizza.service.exception;


/*
 * Este es una exception personalizada que 
 * llama a la exception RuntimException llamando a 
 * su constructor para lanzar la exception.
 */
public class EmailApiException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public EmailApiException() {
		super("Error sending email...");
	}
	
}
