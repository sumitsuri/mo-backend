package com.Spring.SpringBootMysql.exceptions;

import java.util.HashMap;

public class InvalidTokenException extends RuntimeException {

  public InvalidTokenException(String message) {
    super(message);
  }

  public InvalidTokenException(HashMap<String, String> attributeMap) {
    super(attributeMap.toString() + " is not found");
  }
}
