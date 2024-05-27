package com.Spring.SpringBootMysql.domains.internal.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ServiceCategoryCreateRequest {

  private @NotNull String name;

  private String description;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Long categoryId;
}
