package com.paul.ecomerce.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.paul.ecomerce.dto.ErrorResponseDto;
import com.paul.ecomerce.exception.custom.BusinessException;
import com.paul.ecomerce.exception.custom.InsufficientStockException;
import com.paul.ecomerce.exception.custom.ResourceAlreadyExistsException;
import com.paul.ecomerce.exception.custom.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(
                        ResourceNotFoundException ex,
                        HttpServletRequest request) {

                ErrorResponseDto error = new ErrorResponseDto(
                                HttpStatus.NOT_FOUND.name(),
                                HttpStatus.NOT_FOUND.value(),
                                LocalDateTime.now(),
                                request.getRequestURI(),
                                List.of(ex.getMessage()));

                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(BusinessException.class)
        public ResponseEntity<ErrorResponseDto> handleBusinessException(
                        BusinessException ex,
                        HttpServletRequest request) {

                ErrorResponseDto error = new ErrorResponseDto(
                                HttpStatus.CONFLICT.name(),
                                HttpStatus.CONFLICT.value(),
                                LocalDateTime.now(),
                                request.getRequestURI(),
                                List.of(ex.getMessage()));

                return new ResponseEntity<>(error, HttpStatus.CONFLICT);
        }

        @ExceptionHandler(ResourceAlreadyExistsException.class)
        public ResponseEntity<ErrorResponseDto> handleResourceAlreadyExistsException(
                        ResourceAlreadyExistsException ex,
                        HttpServletRequest request) {

                ErrorResponseDto error = new ErrorResponseDto(
                                HttpStatus.CONFLICT.name(),
                                HttpStatus.CONFLICT.value(),
                                LocalDateTime.now(),
                                request.getRequestURI(),
                                List.of(ex.getMessage()));

                return new ResponseEntity<>(error, HttpStatus.CONFLICT);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(
                        MethodArgumentNotValidException ex,
                        HttpServletRequest request) {

                List<String> messages = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                                .toList();

                ErrorResponseDto error = new ErrorResponseDto(
                                HttpStatus.BAD_REQUEST.name(),
                                HttpStatus.BAD_REQUEST.value(),
                                LocalDateTime.now(),
                                request.getRequestURI(),
                                messages);

                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(NoResourceFoundException.class)
        public ResponseEntity<ErrorResponseDto> handleNoResourceFoundException(
                        NoResourceFoundException ex,
                        HttpServletRequest request) {

                ErrorResponseDto error = new ErrorResponseDto(
                                HttpStatus.NOT_FOUND.name(),
                                HttpStatus.NOT_FOUND.value(),
                                LocalDateTime.now(),
                                request.getRequestURI(),
                                List.of("La ruta solicitada no existe: " + request.getRequestURI()));

                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<ErrorResponseDto> handleDataIntegrityViolationException(
                        DataIntegrityViolationException ex,
                        HttpServletRequest request) {

                List<String> messages = List.of("Violación de la integridad de los datos: " + ex.getMostSpecificCause().getMessage());

                ErrorResponseDto error = new ErrorResponseDto(
                                HttpStatus.BAD_REQUEST.name(),
                                HttpStatus.BAD_REQUEST.value(),
                                LocalDateTime.now(),
                                request.getRequestURI(),
                                messages);

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        @ExceptionHandler(InsufficientStockException.class)
        public ResponseEntity<ErrorResponseDto> handleInsufficientStockException(
                        InsufficientStockException ex,
                        HttpServletRequest request) {

                ErrorResponseDto error = new ErrorResponseDto(
                                HttpStatus.CONFLICT.name(),
                                HttpStatus.CONFLICT.value(),
                                LocalDateTime.now(),
                                request.getRequestURI(),
                                List.of(ex.getMessage()));

                return new ResponseEntity<>(error, HttpStatus.CONFLICT);
        }
        

}
