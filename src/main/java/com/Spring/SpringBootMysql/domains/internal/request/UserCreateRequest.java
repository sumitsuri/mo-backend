package com.Spring.SpringBootMysql.domains.internal.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserCreateRequest {

  /**
   * CREATE TABLE `user` ( `id` int NOT NULL AUTO_INCREMENT, `name` varchar(255) NOT NULL,
   * `email`varchar(255) NOT NULL, `phone_number` varchar(100) DEFAULT NULL, org_id int not null,
   * added_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, updated_on TIMESTAMP NOT NULL DEFAULT
   * CURRENT_TIMESTAMP, PRIMARY KEY (`id`), FOREIGN KEY (org_id) references organisation(id) );
   */
  private @NotNull String phoneNumber;

  private String email;

  private String name;

  private @NotNull long orgId;
}
