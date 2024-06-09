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
@Table(name = "user_booking_details")
@Data
@Accessors(chain = true)
public class UserBookingDetailsEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "booking_id")
  private Long bookingId;

  @Column(name = "store_service_id")
  private Long storeServiceId;

  @Column private String status;

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
