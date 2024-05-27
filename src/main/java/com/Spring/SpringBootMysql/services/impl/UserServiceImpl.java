package com.Spring.SpringBootMysql.services.impl;

import com.Spring.SpringBootMysql.constants.Constants;
import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.internal.request.UserCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.UserUpdateRequest;
import com.Spring.SpringBootMysql.entities.jpa.UserEntity;
import com.Spring.SpringBootMysql.exceptions.ResourceNotFoundException;
import com.Spring.SpringBootMysql.repository.UserRepo;
import com.Spring.SpringBootMysql.services.UserService;
import com.Spring.SpringBootMysql.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserServiceImpl implements UserService {

  //  PaginatedMerchantRepo paginatedMerchantRepo;

  private static final ObjectMapper objectMapper =
      new ObjectMapper().registerModule(new JavaTimeModule());
  UserRepo userRepo;
  Utils utils;

  @Autowired
  public UserServiceImpl(UserRepo userRepo, Utils utils) {
    this.userRepo = userRepo;
    this.utils = utils;
  }

  @Transactional
  @Override
  public StatusMessageResponse addUser(UserCreateRequest userCreateRequest) throws Exception {
    UserEntity userEntity = new UserEntity();
    userEntity.setName(userCreateRequest.getName());
    userEntity.setEmail(userCreateRequest.getEmail());
    userEntity.setPhoneNumber(userCreateRequest.getPhoneNumber());
    userEntity.setOrgId(userCreateRequest.getOrgId());
    userRepo.save(userEntity);
    return StatusMessageResponse.builder()
        .data(userEntity)
        .code(HttpStatus.OK.value())
        .message("User added successfully")
        .build();
  }

  @Override
  public StatusMessageResponse getAllUsers() throws Exception {
    List<UserEntity> userEntityList = userRepo.findAll();
    if (CollectionUtils.isEmpty(userEntityList)) {
      userEntityList = new ArrayList<>();
    }
    return StatusMessageResponse.builder()
        .data(userEntityList)
        .code(HttpStatus.OK.value())
        .message("User details fetched")
        .build();
  }

  @Override
  public StatusMessageResponse getUserById(long id) throws Exception {
    Optional<UserEntity> optionalUserEntity = userRepo.findById(id);
    if (!optionalUserEntity.isPresent()) {
      throw new ResourceNotFoundException("User with id: " + id + " not found");
    }
    return StatusMessageResponse.builder()
        .data(optionalUserEntity.get())
        .code(HttpStatus.OK.value())
        .message("User details fetched")
        .build();
  }

  @Transactional
  @Override
  public StatusMessageResponse deleteUserById(long id) throws Exception {
    Optional<UserEntity> optionalUserEntity = userRepo.findById(id);
    if (!optionalUserEntity.isPresent()) {
      throw new ResourceNotFoundException("User with id: " + id + " not found");
    }
    UserEntity userEntity = optionalUserEntity.get();
    userEntity.setStatus(Constants.Status.IN_ACTIVE);
    userRepo.save(userEntity);
    return StatusMessageResponse.builder()
        .data(optionalUserEntity.get())
        .code(HttpStatus.OK.value())
        .message("User deleted successfully")
        .build();
  }

  @Transactional
  @Override
  public StatusMessageResponse updateUserById(long id, UserUpdateRequest userUpdateRequest)
      throws Exception {
    Optional<UserEntity> optionalUserEntity = userRepo.findById(id);
    if (!optionalUserEntity.isPresent()) {
      throw new ResourceNotFoundException("User with id: " + id + " not found");
    }
    UserEntity userEntity = optionalUserEntity.get();
    userEntity.setName(userUpdateRequest.getName());
    userEntity.setEmail(userUpdateRequest.getEmail());
    userEntity.setPhoneNumber(userUpdateRequest.getPhoneNumber());
    userRepo.save(userEntity);
    return StatusMessageResponse.builder()
        .data(optionalUserEntity.get())
        .code(HttpStatus.OK.value())
        .message("User updated successfully")
        .build();
  }
}
