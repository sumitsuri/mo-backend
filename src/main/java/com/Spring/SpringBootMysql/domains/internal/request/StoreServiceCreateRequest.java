package com.Spring.SpringBootMysql.domains.internal.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class StoreServiceCreateRequest {

  private Long storeId;
  private Long serviceId;
  private double estimatedTimeToComplete;
  private double price;

}
