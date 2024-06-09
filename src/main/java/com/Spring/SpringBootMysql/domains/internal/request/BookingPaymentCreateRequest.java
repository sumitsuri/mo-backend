package com.Spring.SpringBootMysql.domains.internal.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingPaymentCreateRequest {

   private @NotNull Long userBookingId;
    private Double discount;
    private String paymentMethod;

}
