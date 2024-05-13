package com.Spring.SpringBootMysql.domains.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationRequest {
    String firstName;
    String lastName;
    String email;
}
