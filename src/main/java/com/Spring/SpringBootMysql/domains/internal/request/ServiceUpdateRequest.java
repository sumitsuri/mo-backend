package com.Spring.SpringBootMysql.domains.internal.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ServiceUpdateRequest {

  private @NotNull String name;
  private String description;
  private String image;
  private double estimatedTimeToComplete;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Long serviceCategoryId;
  private double price;

}
