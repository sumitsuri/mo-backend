package com.Spring.SpringBootMysql.exceptions;

public class ResourceAlreadyExistsException extends Exception {

  public ResourceAlreadyExistsException(final String msg) {
    super(msg);
  }
}
