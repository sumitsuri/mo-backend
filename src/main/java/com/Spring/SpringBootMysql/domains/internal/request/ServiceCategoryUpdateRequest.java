package com.Spring.SpringBootMysql.domains.internal.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ServiceCategoryUpdateRequest {

  private @NotNull String name;

  private String description;

  private long categoryId;
}
