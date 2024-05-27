package com.Spring.SpringBootMysql.exceptions;

public class ValueNotOneOfExpectedEnumValues extends RuntimeException {
  public ValueNotOneOfExpectedEnumValues(String message) {
    super(message);
  }
}
