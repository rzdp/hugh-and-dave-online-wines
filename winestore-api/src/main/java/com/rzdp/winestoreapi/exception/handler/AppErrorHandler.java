package com.rzdp.winestoreapi.exception.handler;

import com.rzdp.winestoreapi.dto.ErrorDto;
import com.rzdp.winestoreapi.dto.response.ErrorResponse;
import com.rzdp.winestoreapi.dto.response.ValidationErrorResponse;
import com.rzdp.winestoreapi.exception.AccountAlreadyExistException;
import com.rzdp.winestoreapi.exception.AccountAlreadyVerifiedException;
import com.rzdp.winestoreapi.exception.ConfirmSignUpException;
import com.rzdp.winestoreapi.exception.DataNotFoundException;
import com.rzdp.winestoreapi.exception.EmailException;
import com.rzdp.winestoreapi.exception.SshException;
import com.rzdp.winestoreapi.exception.UserUpdatePhotoException;
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
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {
        return buildErrorResponse(ex, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
        return buildErrorResponse(ex, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildValidationErrorResponse(status, "Validation Error!",
                ex.getBindingResult().getFieldErrors());
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers,
                                                         HttpStatus status, WebRequest request) {
        return buildValidationErrorResponse(status, "Validation Error!",
                ex.getBindingResult().getFieldErrors());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex,
                                                  WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        List<ErrorDto> errors = new ArrayList<>();
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            ErrorDto detail = new ErrorDto(violation.getPropertyPath().toString(),
                    violation.getInvalidValue(), violation.getMessage());
            errors.add(detail);
        }
        ValidationErrorResponse response =
                new ValidationErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation Error!",
                        errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AccountAlreadyExistException.class)
    public ResponseEntity<Object> handleAccountAlreadyExistException(AccountAlreadyExistException ex,
                                                                     WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = AccountAlreadyVerifiedException.class)
    public ResponseEntity<Object> handleAccountAlreadyVerifiedException(AccountAlreadyVerifiedException ex,
                                                                        WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request);
    }


    @ExceptionHandler(value = ConfirmSignUpException.class)
    public ResponseEntity<Object> handleConfirmSignUpException(ConfirmSignUpException ex,
                                                                        WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request);
    }



    @ExceptionHandler(value = DataNotFoundException.class)
    public ResponseEntity<Object> handleDataNotFoundException(DataNotFoundException ex,
                                                              WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = EmailException.class)
    public ResponseEntity<Object> handleEmailException(EmailException ex, WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = SshException.class)
    public ResponseEntity<Object> handleSshException(SshException ex, WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = UserUpdatePhotoException.class)
    public ResponseEntity<Object> handleUserUpdatePhotoException(UserUpdatePhotoException ex,
                                                                 WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception ex, HttpStatus status,
                                                      WebRequest request) {
        ErrorResponse response = new ErrorResponse(status.value(), ex.getMessage(),
                request.getDescription(false), new Date());
        return new ResponseEntity<>(response, status);
    }

    private ResponseEntity<Object> buildValidationErrorResponse(HttpStatus status, String message
            , List<FieldError> validationErrors) {
        List<ErrorDto> errors = new ArrayList<>();
        for (FieldError validationError : validationErrors) {
            ErrorDto detail = new ErrorDto(validationError.getField(),
                    validationError.getRejectedValue(), validationError.getDefaultMessage());
            errors.add(detail);
        }
        errors.sort(Comparator.comparing(ErrorDto::getField));
        ValidationErrorResponse response = new ValidationErrorResponse(status.value(), message,
                errors);
        return new ResponseEntity<>(response, status);
    }
}
