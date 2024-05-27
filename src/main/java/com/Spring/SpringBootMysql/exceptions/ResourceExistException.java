package com.Spring.SpringBootMysql.exceptions;

import org.springframework.security.core.AuthenticationException;

public class ResourceExistException extends AuthenticationException {

  private static final long serialVersionUID = 1L;

  public ResourceExistException(final String msg) {
    super(msg);
  }
}
