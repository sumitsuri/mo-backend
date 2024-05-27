package com.Spring.SpringBootMysql.exceptions;

public class PhoneAlreadyExistsException extends RuntimeException {
  public PhoneAlreadyExistsException(String message) {
    super(message);
  }
}
