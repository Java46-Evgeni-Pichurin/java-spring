package telran.spring.exceptions;

import jakarta.validation.*;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.*;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<String> handlerMethodArgument(MethodArgumentNotValidException e) {
        List<ObjectError> errors = e.getAllErrors();
        String body = errors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(";"));
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<String> handlerConstraintViolation(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraints = e.getConstraintViolations();
        String body = constraints.stream().map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(";"));
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    ResponseEntity<String> handlerNoSuchElement(NoSuchElementException e) {
        return new ResponseEntity<>("Wrong id number", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> handlerIllegalArgument(IllegalArgumentException e) {
        return new ResponseEntity<>("Illegal argument", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InterruptedException.class)
    ResponseEntity<String> handlerInterrupted(InterruptedException e) {
        return new ResponseEntity<>("Something goes wrong", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ExecutionException.class)
    ResponseEntity<String> handlerExecution(ExecutionException e) {
        return new ResponseEntity<>("Something goes wrong", HttpStatus.CONFLICT);
    }
}
