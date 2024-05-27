package com.Spring.SpringBootMysql.exceptions;

public class UnparseableJsonException extends RuntimeException {
  public UnparseableJsonException(String data) {
    super(data);
  }

  public UnparseableJsonException(String data, Throwable throwable) {
    super(data, throwable);
  }
}
