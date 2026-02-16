package com.pizza.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

        /**
         * Handle custom order exceptions
         */
        @ExceptionHandler(CustomOrderException.class)
        public ResponseEntity<ErrorResponse> handleCustomOrderException(
                        CustomOrderException ex,
                        HttpServletRequest request) {

                ErrorResponse errorResponse = new ErrorResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                "Bad Request",
                                ex.getMessage(),
                                request.getRequestURI());

                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        /**
         * Handle order not found exceptions
         */
        @ExceptionHandler(OrderNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleOrderNotFoundException(
                        OrderNotFoundException ex,
                        HttpServletRequest request) {

                ErrorResponse errorResponse = new ErrorResponse(
                                HttpStatus.NOT_FOUND.value(),
                                "Not Found",
                                ex.getMessage(),
                                request.getRequestURI());

                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        /**
         * Handle external API exceptions
         */
        @ExceptionHandler(ExternalApiException.class)
        public ResponseEntity<ErrorResponse> handleExternalApiException(
                        ExternalApiException ex,
                        HttpServletRequest request) {

                ErrorResponse errorResponse = new ErrorResponse(
                                HttpStatus.BAD_GATEWAY.value(),
                                "Bad Gateway",
                                ex.getMessage(),
                                request.getRequestURI());

                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_GATEWAY);
        }

        /**
         * Handle validation errors from @Valid annotations
         */
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> handleValidationException(
                        MethodArgumentNotValidException ex,
                        HttpServletRequest request) {

                List<String> errors = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                                .collect(Collectors.toList());

                ErrorResponse errorResponse = new ErrorResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                "Validation Failed",
                                "Invalid input data",
                                request.getRequestURI(),
                                errors);

                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        /**
         * Handle constraint violation exceptions
         */
        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<ErrorResponse> handleConstraintViolationException(
                        ConstraintViolationException ex,
                        HttpServletRequest request) {

                List<String> errors = ex.getConstraintViolations()
                                .stream()
                                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                                .collect(Collectors.toList());

                ErrorResponse errorResponse = new ErrorResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                "Constraint Violation",
                                "Validation constraints violated",
                                request.getRequestURI(),
                                errors);

                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        /**
         * Handle type mismatch errors (e.g., passing string when int expected)
         */
        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public ResponseEntity<ErrorResponse> handleTypeMismatchException(
                        MethodArgumentTypeMismatchException ex,
                        HttpServletRequest request) {

                String message = String.format("Parameter '%s' should be of type %s",
                                ex.getName(),
                                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown");

                ErrorResponse errorResponse = new ErrorResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                "Type Mismatch",
                                message,
                                request.getRequestURI());

                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        /**
         * Handle illegal argument exceptions
         */
        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
                        IllegalArgumentException ex,
                        HttpServletRequest request) {

                ErrorResponse errorResponse = new ErrorResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                "Invalid Argument",
                                ex.getMessage(),
                                request.getRequestURI());

                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        /**
         * Handle null pointer exceptions
         */
        @ExceptionHandler(NullPointerException.class)
        public ResponseEntity<ErrorResponse> handleNullPointerException(
                        NullPointerException ex,
                        HttpServletRequest request) {

                ErrorResponse errorResponse = new ErrorResponse(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "Internal Server Error",
                                "An unexpected error occurred. Please contact support.",
                                request.getRequestURI());

                return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        /**
         * Handle all other uncaught exceptions
         */
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleGenericException(
                        Exception ex,
                        HttpServletRequest request) {

                ErrorResponse errorResponse = new ErrorResponse(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "Internal Server Error",
                                "An unexpected error occurred: " + ex.getMessage(),
                                request.getRequestURI());

                return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
}
