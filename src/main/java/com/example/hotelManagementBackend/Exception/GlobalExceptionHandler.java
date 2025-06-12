package com.example.hotelManagementBackend.Exception;

import com.example.hotelManagementBackend.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                ex.getStatus().value(),
                new Date()
        );
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, List<String>> validError = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                ));

        ErrorResponse errorResponse = new ErrorResponse(
                "Validation Failed", HttpStatus.BAD_REQUEST.value(), new Date(), validError);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            RoomNotAvailableException.class,
            RoomNotFoundException.class,
            InvalidDateRangeException.class,
            WrongPasswordException.class
    })
    public ResponseEntity<ErrorResponse> determineException(RuntimeException ex) {
        HttpStatus status = determineHttpStatus(ex);
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                status.value(),
                new Date()
        );
        return new ResponseEntity<>(errorResponse, status);
    }

    private HttpStatus determineHttpStatus(RuntimeException ex) {
        if (ex instanceof RoomNotAvailableException) {
            return HttpStatus.CONFLICT;
        } else if (ex instanceof RoomNotFoundException) {
            return HttpStatus.NOT_FOUND;
        } else if (ex instanceof InvalidDateRangeException) {
            return HttpStatus.BAD_REQUEST;
        } else if (ex instanceof WrongPasswordException) {
            return HttpStatus.UNAUTHORIZED;
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
