package com.meuboletim.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ExceptionDefault.class)
	public ResponseEntity<StandardError> entidadeNaoEncontrada(ExceptionDefault e, HttpServletRequest req) {
		StandardError err = new StandardError();
		err.setStatus(HttpStatus.BAD_REQUEST.value());
		err.setMessage(e.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

}