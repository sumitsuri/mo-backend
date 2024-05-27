package com.Spring.SpringBootMysql.domains.internal.request;

import lombok.Data;


@Data
public class OrgLocationStoreUpdateRequest {

  private String name;
  private String email;
  private String phoneNumber;
  
}
