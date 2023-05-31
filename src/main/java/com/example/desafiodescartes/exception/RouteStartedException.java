package com.example.desafiodescartes.exception;

public class RouteStartedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RouteStartedException() {
		super();
	}

	public RouteStartedException(String msg) {
		super(msg);
	}
}
