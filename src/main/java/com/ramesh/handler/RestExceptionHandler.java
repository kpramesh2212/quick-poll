package com.ramesh.handler;

import com.ramesh.error.ErrorDetail;
import com.ramesh.error.ValidationError;
import com.ramesh.exception.ResourceNotFoundException;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @Inject
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException
                                                                              ex, HttpHeaders
            headers, HttpStatus status, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTitle("Message Not Readable");
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setDeveloperMessage(ex.getClass().getName());
        errorDetail.setStatus(status.value());
        errorDetail.setTimestamp(new Date().getTime());
        return handleExceptionInternal(ex, errorDetail, headers, status, request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rnfe) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setTitle("Resource Not Found");
        errorDetail.setDetail(rnfe.getMessage());
        errorDetail.setDeveloperMessage(rnfe.getClass().getName());
        return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException
                                                                          manve, HttpHeaders
                                                                          headers, HttpStatus
                                                                          status, WebRequest
                                                                          request) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().getTime());
        errorDetail.setTitle("Validation Failed");
        errorDetail.setDeveloperMessage(manve.getClass().getName());
        errorDetail.setDetail("Input validation failed");

        for (FieldError fieldError : manve.getBindingResult().getFieldErrors()) {
            List<ValidationError> validationErrorList = errorDetail.getErrors().get(fieldError
                    .getField());
            if (validationErrorList == null) {
                validationErrorList = new ArrayList<>();
                errorDetail.getErrors().put(fieldError.getField(), validationErrorList);
            }

            ValidationError validationError = new ValidationError();
            validationError.setCode(fieldError.getCode());
            validationError.setMessage(messageSource.getMessage(fieldError, null));
            validationErrorList.add(validationError);
        }
        return handleExceptionInternal(manve, errorDetail, headers, status, request);
    }
}
