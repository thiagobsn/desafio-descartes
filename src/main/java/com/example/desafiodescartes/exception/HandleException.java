package com.example.desafiodescartes.exception;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.desafiodescartes.util.MessageBundle;

@RestControllerAdvice
public class HandleException {

	@Autowired
	public MessageBundle messageBundle;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleErro400(MethodArgumentNotValidException ex) {

		var erros = ex.getFieldErrors().stream().map(ErroDTO::new);
		return ResponseEntity.badRequest().body(erros);
	}

	@ExceptionHandler(InvalidLatitudeLongitudeException.class)
	public ResponseEntity<?> handleErroInvalidLatitudeLongitudeException(InvalidLatitudeLongitudeException ex) {
		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
				.body(messageBundle.getString("route.erro.lat.log.invalid"));
	}

	@ExceptionHandler(RouteStartedException.class)
	public ResponseEntity<?> handleErroRouteStartedException(RouteStartedException ex) {
		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
				.body(messageBundle.getString("route.erro.delete.route.started.or.done"));
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<?> handleErroRouteStartedException(NoSuchElementException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	public record ErroDTO(String campo, String mensagem) {
		public ErroDTO(FieldError error) {
			this(error.getField(), error.getDefaultMessage());
		}
	}

}
