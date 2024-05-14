package com.Spring.SpringBootMysql.domains.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SignUpRequest {


    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    private String email;
}
