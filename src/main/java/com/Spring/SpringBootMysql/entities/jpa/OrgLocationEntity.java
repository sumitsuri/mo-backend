package com.Spring.SpringBootMysql.entities.jpa;

import com.Spring.SpringBootMysql.constants.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "organisation_location")
@Data
@Accessors(chain = true)
public class OrgLocationEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column private String city;
  @Column private String state;
  @Column private String address;
  @Column private String pincode;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "status")
  private String status = Constants.Status.ACTIVE;

  @Column(name = "org_id")
  private Long orgId;

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
