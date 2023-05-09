package pl.bartkn.ztpai.exception;

import io.jsonwebtoken.ExpiredJwtException;
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
            RuntimeException ex, WebRequest request
    ) {
        String body;
        if (ex.getMessage().isEmpty()) {
            body = "Username or email taken";
        } else {
            body = ex.getMessage();
        }

        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value =
            {UserNotFoundException.class})
    protected ResponseEntity<Object> handleUserNotFoundException (
            RuntimeException ex, WebRequest request
    ) {
        String body = "User not found";
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value =
            {TokenNotFoundException.class})
    protected ResponseEntity<Object> handleTokenNotFoundException (
            RuntimeException ex, WebRequest request
    ) {
        String body = "Token not found";
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value =
            {ExpiredJwtException.class})
    protected ResponseEntity<Object> handleExpiredTokenException (
            RuntimeException ex, WebRequest request
    ) {
        String body = "Token is expired";
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }
}
