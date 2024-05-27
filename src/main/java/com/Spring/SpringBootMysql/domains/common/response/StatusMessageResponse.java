package com.Spring.SpringBootMysql.domains.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Builder
public class StatusMessageResponse {
  String status = "SUCCESS";

  @JsonInclude(JsonInclude.Include.NON_NULL)
  Integer code;

  String message;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  Object data;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  String error;
}
