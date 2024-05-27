package com.Spring.SpringBootMysql.exceptions;

public class ResourceNotFoundExceptionV2 extends RuntimeException {

  public ResourceNotFoundExceptionV2(String message) {
    super(message);
  }
}
