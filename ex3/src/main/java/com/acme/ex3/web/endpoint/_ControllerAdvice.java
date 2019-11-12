package com.acme.ex3.web.endpoint;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class _ControllerAdvice {

/*    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> handlerBindException(BindException exception){
//        BindingResult br = exception.getBindingResult();
//        List<ValidationErrors> validationErrors = onValidationError(br.getFieldErrors());
        List<Map<String, String>> field = exception.getBindingResult().getFieldErrors().stream()
                .map(fe -> )
        return ResponseEntity.badRequest().build();
    }

    private List<ValidationErrors> onValidationError(List<FieldError> fieldErrors) {
//        return fieldErrors.stream().map(ValidationError::new).collect(Collectors.toList());
        return null;
    }*/

/*    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> onBindException(BindException e){
        List<Object> errors = e.getBindingResult().getFieldErrors().stream()
                .map(fe -> new Object(){{
                    String field = fe.getField();
                    String message = fe.getDefaultMessage();
                }})
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errors);
    }*/

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> onBindException(BindException e) throws JsonProcessingException {
        List<Map<String, String>> errors = e.getBindingResult().getFieldErrors().stream()
                .map(fe -> Map.of(
                        "field", fe.getField(),
                        "message", fe.getDefaultMessage()
                        )
                ).collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errors);
    }
}
