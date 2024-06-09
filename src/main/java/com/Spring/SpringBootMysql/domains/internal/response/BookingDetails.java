package com.Spring.SpringBootMysql.domains.internal.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
@Builder
public class BookingDetails {

  private Long storeId;

  private String status;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String notes;

  private LocalDateTime bookingStartTime;

  private LocalDateTime addedOn;

  private LocalDateTime updatedOn;

  List<BookingLineItem> bookingLineItems;

  @Builder
  public static class BookingLineItem{
    Long id;

    private Long storeServiceId;

    private String status;
  }
}
