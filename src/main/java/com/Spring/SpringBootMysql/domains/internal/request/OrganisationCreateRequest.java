package com.Spring.SpringBootMysql.domains.internal.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrganisationCreateRequest {

  private @NotNull String name;

  private String email;

  private String phoneNumber;
}
