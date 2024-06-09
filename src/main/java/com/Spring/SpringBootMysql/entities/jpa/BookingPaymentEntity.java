package com.Spring.SpringBootMysql.entities.jpa;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "booking_payment")
@Data
@Accessors(chain = true)
public class BookingPaymentEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_booking_id")
  private Long userBookingId;

  @Column private Double discount;

  @Column(name = "total_amount")
  private Double totalAmount;

  @Column(name = "paid_amount")
  private Double paidAmount;

  @Column(name = "payment_method")
  private String paymentMethod;

  @Column(name = "added_by")
  private int addedBy;

  @Column(name = "updated_by")
  private int updatedBy;

  @Column(name = "added_on")
  @CreationTimestamp
  @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
  private LocalDateTime addedOn;

  @Column(name = "updated_on")
  @UpdateTimestamp
  @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private LocalDateTime updatedOn;

}
