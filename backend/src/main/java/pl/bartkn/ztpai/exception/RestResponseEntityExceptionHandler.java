package pl.bartkn.ztpai.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value =
            {EmailOrUsernameTakenException.class})
    protected ResponseEntity<Object> handleEmailOrUsernameTakenException (
            RuntimeException ex, WebRequest request) {
        String body = "Username or email taken";
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value =
            {UserNotFoundException.class})
    protected ResponseEntity<Object> handleUserNotFoundException (
            RuntimeException ex, WebRequest request) {
        String body = "User not found";
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
