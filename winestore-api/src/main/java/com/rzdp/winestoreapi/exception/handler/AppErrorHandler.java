package com.rzdp.winestoreapi.exception.handler;

import com.rzdp.winestoreapi.dto.ErrorDto;
import com.rzdp.winestoreapi.dto.response.ErrorResponse;
import com.rzdp.winestoreapi.dto.response.ValidationErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

@ControllerAdvice
public class AppErrorHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildErrorResponse(ex, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildErrorResponse(ex, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildValidationErrorResponse(status, "Validation Error!", ex.getBindingResult().getFieldErrors());
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildValidationErrorResponse(status, "Validation Error!", ex.getBindingResult().getFieldErrors());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        List<ErrorDto> errors = new ArrayList<>();
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            ErrorDto detail = new ErrorDto(violation.getPropertyPath().toString(), violation.getInvalidValue(), violation.getMessage());
            errors.add(detail);
        }
        ValidationErrorResponse response = new ValidationErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation Error!", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception ex, HttpStatus status, WebRequest request) {
        ErrorResponse response = new ErrorResponse(status.value(), ex.getMessage(), request.getDescription(false), new Date());
        return new ResponseEntity<>(response, status);
    }

    private ResponseEntity<Object> buildValidationErrorResponse(HttpStatus status, String message, List<FieldError> validationErrors) {
        List<ErrorDto> errors = new ArrayList<>();
        for (FieldError validationError : validationErrors) {
            ErrorDto detail = new ErrorDto(validationError.getField(), validationError.getRejectedValue(), validationError.getDefaultMessage());
            errors.add(detail);
        }
        errors.sort(Comparator.comparing(ErrorDto::getField));
        ValidationErrorResponse response = new ValidationErrorResponse(status.value(), message, errors);
        return new ResponseEntity<>(response, status);
    }
}
