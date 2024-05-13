package com.Spring.SpringBootMysql.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "Merchants")
@Data
@Accessors(chain = true)
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column private String name;

  @Column private String email;

  @Column private String phone;

  @Column
  @CreationTimestamp
  @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
  private LocalDateTime addedOn;

  @Column
  @UpdateTimestamp
  @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private LocalDateTime updatedOn;

  @Column private boolean isActive;


//  @Column
//  @Enumerated(EnumType.STRING)
//  private MerchantRiskLevel riskLevel;
//
//  @Column
//  @Enumerated(EnumType.STRING)
//  private MerchantStatus status;

//  @Override
//  public Map<String, String> getDataForAudit() {
//    HashMap<String, String> dataForAudit = new HashMap<>();
//    dataForAudit.put(identifier.name(), String.valueOf(this.id));
//    dataForAudit.put(entityType.name(), "MERCHANT_ENTITY");
//    dataForAudit.put(addedBy.name(), this.getMerchantName());
//    return dataForAudit;
//  }
}
