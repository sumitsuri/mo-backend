package com.Spring.SpringBootMysql.domains.internal.response;

import com.Spring.SpringBootMysql.domains.custom.PaymentDetails;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
@Builder
public class OverallBill {

  private Double totalAmount;
  private Long bookingId;
  private List<PaymentDetails> paymentDetails;

}
