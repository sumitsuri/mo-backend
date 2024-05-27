package com.Spring.SpringBootMysql.exceptions;

import java.util.HashMap;

public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(String message) {
    super(message);
  }

  public ResourceNotFoundException(HashMap<String, String> attributeMap) {
    super(attributeMap.toString() + " is not found");
  }
}
