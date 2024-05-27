package com.Spring.SpringBootMysql.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserAlreadyExistAuthenticationException extends AuthenticationException {

  private static final long serialVersionUID = 1L;

  public UserAlreadyExistAuthenticationException(final String msg) {
    super(msg);
  }

  public UserAlreadyExistAuthenticationException(final String msg, Throwable t) {
    super(msg, t);
  }
}
