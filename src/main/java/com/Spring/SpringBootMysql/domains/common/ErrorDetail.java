package com.Spring.SpringBootMysql.domains.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDetail {
  String status;
  String title;
  String message;
  String statusCode;
}
