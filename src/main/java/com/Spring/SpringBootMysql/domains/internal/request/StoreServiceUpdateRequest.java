package com.Spring.SpringBootMysql.domains.internal.request;

import lombok.Data;

@Data
public class StoreServiceUpdateRequest {

  private double estimatedTimeToComplete;
  private double price;

}
