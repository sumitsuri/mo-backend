package com.Spring.SpringBootMysql.domains.internal.request;

import lombok.Data;

@Data
public class UserMetadataCreateRequest {

  private String gender;

  private int age;

  private long userId;
}
