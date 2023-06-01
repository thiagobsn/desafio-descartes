package com.example.desafiodescartes.util;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

@Component
public class ManualValidator {

	@Autowired
	private Validator validator;

	public <T> void validate(T object, Class<?>... groups) {

		Set<ConstraintViolation<T>> violations = validator.validate(object, groups);

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}
	}

	public <T> void validate(List<T> objects, Class<?>... groups) {
		objects.forEach(object -> validate(object, groups));
	}
}

