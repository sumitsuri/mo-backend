package com.Spring.SpringBootMysql.exceptions;

public class PhoneNumberWrongException extends RuntimeException {
  public PhoneNumberWrongException(String message) {
    super(message);
  }
}
