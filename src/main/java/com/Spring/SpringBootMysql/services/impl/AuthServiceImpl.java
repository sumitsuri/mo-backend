package com.Spring.SpringBootMysql.services.impl;

import com.Spring.SpringBootMysql.constants.Constants;
import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.dto.LoginRequest;
import com.Spring.SpringBootMysql.domains.internal.request.SignupRequest;
import com.Spring.SpringBootMysql.domains.internal.request.UserCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.UserUpdateRequest;
import com.Spring.SpringBootMysql.entities.jpa.UserEntity;
import com.Spring.SpringBootMysql.exception.DuplicatedUserInfoException;
import com.Spring.SpringBootMysql.exceptions.ResourceNotFoundException;
import com.Spring.SpringBootMysql.repository.UserRepo;
import com.Spring.SpringBootMysql.security.TokenProvider;
import com.Spring.SpringBootMysql.services.AuthService;
import com.Spring.SpringBootMysql.services.UserService;
import com.Spring.SpringBootMysql.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class AuthServiceImpl implements AuthService {

  //  PaginatedMerchantRepo paginatedMerchantRepo;

  private static final ObjectMapper objectMapper =
      new ObjectMapper().registerModule(new JavaTimeModule());
  UserService userService;
  Utils utils;

  private PasswordEncoder passwordEncoder;
  private AuthenticationManager authenticationManager;
  private TokenProvider tokenProvider;

  @Autowired
  public AuthServiceImpl(UserService userService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenProvider tokenProvider, Utils utils) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.tokenProvider = tokenProvider;
    this.utils = utils;
  }


  @Override
  public StatusMessageResponse login(LoginRequest loginRequest) throws Exception {
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
     String token = tokenProvider.generate(authentication);
    return StatusMessageResponse.builder()
            .data(token)
            .code(HttpStatus.OK.value())
            .message("Login successful")
            .build();
  }

  @Override
  public StatusMessageResponse signUp(SignupRequest signupRequest) throws Exception {
    if (userService.hasUserWithEmail(signupRequest.getEmail())) {
      throw new DuplicatedUserInfoException(String.format("Email %s already been used", signupRequest.getEmail()));
    }
    if (userService.hasUserWithPhoneNumber(signupRequest.getPhoneNumber())) {
      throw new DuplicatedUserInfoException(String.format("Phone %s already been used", signupRequest.getPhoneNumber()));
    }

    userService.addUser(signupRequest);
    return login(new LoginRequest(signupRequest.getEmail(), signupRequest.getPassword()));
  }
}
