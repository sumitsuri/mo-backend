package com.Spring.SpringBootMysql.domains.internal.request;

import lombok.Data;

@Data
public class OrganisationUpdateRequest {

  private String name;

  private String email;

  private String phoneNumber;
}
