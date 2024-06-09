package com.Spring.SpringBootMysql.services.impl;

import com.Spring.SpringBootMysql.constants.Constants;
import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.internal.request.UserCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.UserRoleAddRequest;
import com.Spring.SpringBootMysql.domains.internal.request.UserUpdateRequest;
import com.Spring.SpringBootMysql.entities.jpa.OrgLocationEntity;
import com.Spring.SpringBootMysql.entities.jpa.UserEntity;
import com.Spring.SpringBootMysql.entities.jpa.UserRoleEntity;
import com.Spring.SpringBootMysql.exceptions.ResourceNotFoundException;
import com.Spring.SpringBootMysql.repository.UserRepo;
import com.Spring.SpringBootMysql.repository.UserRoleRepo;
import com.Spring.SpringBootMysql.services.UserRoleService;
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
public class UserRoleServiceImpl implements UserRoleService {

  //  PaginatedMerchantRepo paginatedMerchantRepo;

  private static final ObjectMapper objectMapper =
      new ObjectMapper().registerModule(new JavaTimeModule());
  UserRoleRepo userRoleRepo;
  Utils utils;

  @Autowired
  public UserRoleServiceImpl(UserRoleRepo userRoleRepo, Utils utils) {
    this.userRoleRepo = userRoleRepo;
    this.utils = utils;
  }

  @Transactional
  @Override
  public StatusMessageResponse addUserRole(UserRoleAddRequest userRoleAddRequest) throws Exception {
    UserRoleEntity userRoleEntity = new UserRoleEntity();
    userRoleEntity.setRoleId(userRoleAddRequest.getRoleId());
    userRoleEntity.setUserId(userRoleAddRequest.getUserId());
    userRoleEntity.setAddedBy(-1);
    userRoleEntity.setUpdatedBy(-1);
    userRoleRepo.save(userRoleEntity);

    return StatusMessageResponse.builder()
            .data(userRoleEntity)
            .code(HttpStatus.OK.value())
            .message("User Role added successfully")
            .build();
  }

  @Override
  public StatusMessageResponse getUserRoles(long userId) throws Exception {
    Optional<List<UserRoleEntity>> optionalUserRoles = userRoleRepo.findByUserId(userId);
    List<UserRoleEntity> userRoles = new ArrayList<>();
    if (optionalUserRoles.isPresent()) {
      userRoles = optionalUserRoles.get();
    }
    return StatusMessageResponse.builder()
            .data(userRoles)
            .code(HttpStatus.OK.value())
            .message("User Roles details fetched")
            .build();
  }

  @Transactional
  @Override
  public StatusMessageResponse removeUserRole(long id) throws Exception {
    Optional<UserRoleEntity> optionalUserRoleEntity = userRoleRepo.findById(id);
    if (!optionalUserRoleEntity.isPresent()) {
      throw new ResourceNotFoundException("User Role with id: " + id + " not found");
    }
    UserRoleEntity userRoleEntity = optionalUserRoleEntity.get();
    userRoleEntity.setStatus(Constants.Status.IN_ACTIVE);
    userRoleRepo.save(userRoleEntity);
    return StatusMessageResponse.builder()
            .data(optionalUserRoleEntity.get())
            .code(HttpStatus.OK.value())
            .message("User Role removed successfully")
            .build();
  }
}
