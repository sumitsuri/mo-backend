package com.Spring.SpringBootMysql.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

  Long id;
  String username;
  String name;
  String email;
  String role;
}
