package com.Spring.SpringBootMysql.domains.internal.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class UserBookingDetailsCreateRequest {

  /**
   @Column(name = "user_id")
   private Long userId;

   @Column(name = "store_service_id")
   private Long storeServiceId;

   @Column private String status;

   @Column private String notes;

   @Column(name = "booking_start_time")
   @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
   private LocalDateTime bookingStartTime;
   */

  private @NotNull Long storeServiceId;

}
