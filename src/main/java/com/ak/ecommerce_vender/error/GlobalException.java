package com.ak.ecommerce_vender.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ak.ecommerce_vender.domain.responce.RestResponce;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(
        value = {
            EmailAlreadyExistsException.class,
            VerificationException.class,
            EntityNotExistException.class,
            UserNotEnabledException.class,
            InvalidIdException.class
        })
    public ResponseEntity<RestResponce<Object>> badRequestException(Exception ex) {
      RestResponce<Object> res = new RestResponce<Object>();
      res.setStatusCode(HttpStatus.BAD_REQUEST.value());
      res.setError(ex.getMessage());
      res.setMessage("Exception occurs...");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(
      value = {
        UsernameNotFoundException.class,
        BadCredentialsException.class
      })
  public ResponseEntity<RestResponce<Object>> handleAuthenticationException(Exception ex) {
    RestResponce<Object> res = new RestResponce<Object>();
    res.setStatusCode(HttpStatus.BAD_REQUEST.value());
    res.setError(ex.getMessage());
    res.setMessage("Exception occurs...");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
  }
}
