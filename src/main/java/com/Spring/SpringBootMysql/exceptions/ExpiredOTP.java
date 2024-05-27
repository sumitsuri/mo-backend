package com.Spring.SpringBootMysql.exceptions;

public class ExpiredOTP extends RuntimeException {
  public ExpiredOTP(String message) {
    super(message);
  }
}
