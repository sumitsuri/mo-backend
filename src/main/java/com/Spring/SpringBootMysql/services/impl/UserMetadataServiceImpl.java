package com.Spring.SpringBootMysql.services.impl;

import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.internal.request.UserMetadataCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.UserMetadataUpdateRequest;
import com.Spring.SpringBootMysql.entities.jpa.UserMetadataEntity;
import com.Spring.SpringBootMysql.exceptions.ResourceNotFoundException;
import com.Spring.SpringBootMysql.repository.UserMetadataRepo;
import com.Spring.SpringBootMysql.services.UserMetadataService;
import com.Spring.SpringBootMysql.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Slf4j
public class UserMetadataServiceImpl implements UserMetadataService {

  private static final ObjectMapper objectMapper =
      new ObjectMapper().registerModule(new JavaTimeModule());
  UserMetadataRepo userMetadataRepo;
  Utils utils;

  @Autowired
  public UserMetadataServiceImpl(UserMetadataRepo userMetadataRepo, Utils utils) {
    this.userMetadataRepo = userMetadataRepo;
    this.utils = utils;
  }

  @Transactional
  @Override
  public StatusMessageResponse addUserMetadata(UserMetadataCreateRequest userMetadataCreateRequest)
      throws Exception {
    UserMetadataEntity userMetadataEntity = new UserMetadataEntity();
    userMetadataEntity.setAge(userMetadataCreateRequest.getAge());
    userMetadataEntity.setGender(userMetadataCreateRequest.getGender());
    userMetadataEntity.setUserId(userMetadataCreateRequest.getUserId());
    userMetadataRepo.save(userMetadataEntity);
    return StatusMessageResponse.builder()
        .data(userMetadataEntity)
        .code(HttpStatus.OK.value())
        .message("UserMetadata added successfully")
        .build();
  }

  @Override
  public StatusMessageResponse getUserMetadataByUserId(long userId) throws Exception {
    Optional<UserMetadataEntity> optionalUserEntity = userMetadataRepo.findByUserId(userId);
    if (!optionalUserEntity.isPresent()) {
      throw new ResourceNotFoundException("UserMetadata with userid: " + userId + " not found");
    }
    return StatusMessageResponse.builder()
        .data(optionalUserEntity.get())
        .code(HttpStatus.OK.value())
        .message("User details fetched")
        .build();
  }

  @Transactional
  @Override
  public StatusMessageResponse updateUserMetadataById(
      long id, UserMetadataUpdateRequest userMetadataUpdateRequest) throws Exception {
    Optional<UserMetadataEntity> optionalUserMetadataEntity = userMetadataRepo.findById(id);
    if (!optionalUserMetadataEntity.isPresent()) {
      throw new ResourceNotFoundException("UserMetadata with id: " + id + " not found");
    }
    UserMetadataEntity userMetadataEntity = optionalUserMetadataEntity.get();
    userMetadataEntity.setAge(userMetadataUpdateRequest.getAge());
    userMetadataEntity.setGender(userMetadataUpdateRequest.getGender());
    userMetadataRepo.save(userMetadataEntity);
    return StatusMessageResponse.builder()
        .data(optionalUserMetadataEntity.get())
        .code(HttpStatus.OK.value())
        .message("UserMetadata updated successfully")
        .build();
  }
}
