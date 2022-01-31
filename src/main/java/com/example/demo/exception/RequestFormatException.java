package com.example.demo.exception;

public class RequestFormatException extends RuntimeException {
	public RequestFormatException(String message) {
		super(message);
	}
}
