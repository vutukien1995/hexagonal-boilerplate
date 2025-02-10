package com.kien.hexagonal_boilerplate.infrastructure.handler;

import com.kien.hexagonal_boilerplate.domain.exception.BadRequestException;
import com.kien.hexagonal_boilerplate.infrastructure.common.BadResponse;
import com.kien.hexagonal_boilerplate.infrastructure.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageUtil messageUtil;

    public GlobalExceptionHandler(MessageUtil messageUtil) {
        this.messageUtil = messageUtil;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<BadResponse.Error> errors = new ArrayList<>();
        BadResponse response = BadResponse.builder()
                .success(false)
                .build();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.add(messageUtil.getErrorByFieldErr(error)));
        response.setErrors(errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestExceptions(BadRequestException ex) {
        return new ResponseEntity<>(messageUtil.getBadResponseByMsg(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
