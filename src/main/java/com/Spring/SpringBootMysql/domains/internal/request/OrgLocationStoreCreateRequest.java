package com.Spring.SpringBootMysql.domains.internal.request;

import jakarta.persistence.Column;
import lombok.Data;


@Data
public class OrgLocationStoreCreateRequest {

  private String storeIdentifier;
  private String name;
  private String type;
  private String email;
  private String phoneNumber;
  private Long orgLocationId;

}
