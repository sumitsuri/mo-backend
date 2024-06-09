package com.Spring.SpringBootMysql.services.impl;

import com.Spring.SpringBootMysql.constants.Constants;
import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.internal.request.RoleCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.UserCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.UserUpdateRequest;
import com.Spring.SpringBootMysql.entities.jpa.RoleEntity;
import com.Spring.SpringBootMysql.entities.jpa.UserEntity;
import com.Spring.SpringBootMysql.exceptions.ResourceNotFoundException;
import com.Spring.SpringBootMysql.repository.RoleRepo;
import com.Spring.SpringBootMysql.repository.UserRepo;
import com.Spring.SpringBootMysql.services.RoleService;
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
public class RoleServiceImpl implements RoleService {

  //  PaginatedMerchantRepo paginatedMerchantRepo;

  private static final ObjectMapper objectMapper =
      new ObjectMapper().registerModule(new JavaTimeModule());
  RoleRepo roleRepo;
  Utils utils;

  @Autowired
  public RoleServiceImpl(RoleRepo roleRepo, Utils utils) {
    this.roleRepo = roleRepo;
    this.utils = utils;
  }

  @Transactional
  @Override
  public StatusMessageResponse addRole(RoleCreateRequest roleCreateRequest) throws Exception {
    RoleEntity roleEntity = new RoleEntity();
    roleEntity.setName(roleCreateRequest.getName());
    roleEntity.setAddedBy(-1);
    roleEntity.setUpdatedBy(-1);
    roleRepo.save(roleEntity);

    return StatusMessageResponse.builder()
            .data(roleEntity)
            .code(HttpStatus.OK.value())
            .message("Role added successfully")
            .build();
  }

  @Override
  public StatusMessageResponse getAllRoles() throws Exception {
    List<RoleEntity> roleEntityList = roleRepo.findAll();
    if (CollectionUtils.isEmpty(roleEntityList)) {
      roleEntityList = new ArrayList<>();
    }
    return StatusMessageResponse.builder()
            .data(roleEntityList)
            .code(HttpStatus.OK.value())
            .message("Role details fetched")
            .build();
  }

  @Override
  public StatusMessageResponse getRoleById(long id) throws Exception {
    Optional<RoleEntity> optionalRoleEntity = roleRepo.findById(id);
    if (!optionalRoleEntity.isPresent()) {
      throw new ResourceNotFoundException("Role with id: " + id + " not found");
    }
    return StatusMessageResponse.builder()
            .data(optionalRoleEntity.get())
            .code(HttpStatus.OK.value())
            .message("Role details fetched")
            .build();
  }

  @Transactional
  @Override
  public StatusMessageResponse deleteRoleById(long id) throws Exception {
    Optional<RoleEntity> optionalRoleEntity = roleRepo.findById(id);
    if (!optionalRoleEntity.isPresent()) {
      throw new ResourceNotFoundException("Role with id: " + id + " not found");
    }
    RoleEntity roleEntity = optionalRoleEntity.get();
    roleEntity.setStatus(Constants.Status.IN_ACTIVE);
    roleRepo.save(roleEntity);
    return StatusMessageResponse.builder()
            .data(optionalRoleEntity.get())
            .code(HttpStatus.OK.value())
            .message("Role deleted successfully")
            .build();
  }
}
