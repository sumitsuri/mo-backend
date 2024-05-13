package com.Spring.SpringBootMysql.domains.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

    int id;
    String firstName;
    String lastName;
    String email;

}
