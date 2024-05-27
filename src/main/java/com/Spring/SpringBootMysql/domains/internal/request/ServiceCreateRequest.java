package com.Spring.SpringBootMysql.domains.internal.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ServiceCreateRequest {

  private @NotNull String name;
  private String description;
  private String image;
  private double estimatedTimeToComplete;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Long serviceCategoryId;
  private double price;

}
