package br.com.reserve.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ElementKind;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.reserve.response.Response;

@ControllerAdvice
public class ControllerAdviceResponse {
		
	@ExceptionHandler({javax.validation.ConstraintViolationException.class})
    public ResponseEntity<Response<String>> constraintViolationExceptionHandler(javax.validation.ConstraintViolationException e, HttpServletRequest request) {
        Response<String> response = new Response<>();
        List<String> listErros = new ArrayList<>();

        for (var violation : e.getConstraintViolations()) {
            for (var node : violation.getPropertyPath()) {
                if (node.getKind().equals(ElementKind.PROPERTY)) {
                	listErros.add(violation.getMessage());
                }
            }
        }

        response.setErrors(listErros);
        return ResponseEntity.badRequest().body(response);
    }
	
}
