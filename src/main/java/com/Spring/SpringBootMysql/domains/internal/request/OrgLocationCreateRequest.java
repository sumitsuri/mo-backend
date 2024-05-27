package com.Spring.SpringBootMysql.domains.internal.request;

import lombok.Data;


@Data
public class OrgLocationCreateRequest {

  private String city;
  private String state;
  private String pincode;
  private String address;
  private String phoneNumber;
  private Long orgId;
}
