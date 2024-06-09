package com.Spring.SpringBootMysql.controller;

import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.dto.LoginRequest;
import com.Spring.SpringBootMysql.domains.internal.request.*;
import com.Spring.SpringBootMysql.services.AuthService;
import com.Spring.SpringBootMysql.services.UserMetadataService;
import com.Spring.SpringBootMysql.services.UserRoleService;
import com.Spring.SpringBootMysql.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/auth")
@Slf4j
public class AuthController {
  @Autowired private AuthService authService;
  @Autowired private UserService userService;


  @PostMapping("/login")
  public StatusMessageResponse login(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
    return authService.login(loginRequest);
  }

  @PostMapping("/signup")
  public StatusMessageResponse createUser(
          @RequestBody @Valid SignupRequest signupRequest) throws Exception {
    return authService.signUp(signupRequest);
  }

}
