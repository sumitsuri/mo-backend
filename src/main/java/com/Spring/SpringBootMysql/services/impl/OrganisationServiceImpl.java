package com.Spring.SpringBootMysql.services.impl;

import com.Spring.SpringBootMysql.constants.Constants;
import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.internal.request.OrganisationCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.OrganisationUpdateRequest;
import com.Spring.SpringBootMysql.entities.jpa.OrganisationEntity;
import com.Spring.SpringBootMysql.exceptions.ResourceNotFoundException;
import com.Spring.SpringBootMysql.repository.OrganisationRepo;
import com.Spring.SpringBootMysql.services.OrganisationService;
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
public class OrganisationServiceImpl implements OrganisationService {

  //  PaginatedMerchantRepo paginatedMerchantRepo;

  private static final ObjectMapper objectMapper =
      new ObjectMapper().registerModule(new JavaTimeModule());
  OrganisationRepo organisationRepo;
  Utils utils;

  @Autowired
  public OrganisationServiceImpl(OrganisationRepo organisationRepo, Utils utils) {
    this.organisationRepo = organisationRepo;
    this.utils = utils;
  }

  @Transactional
  @Override
  public StatusMessageResponse addOrganisation(OrganisationCreateRequest organisationCreateRequest)
      throws Exception {
    OrganisationEntity organisationEntity = new OrganisationEntity();
    organisationEntity.setName(organisationCreateRequest.getName());
    organisationEntity.setEmail(organisationCreateRequest.getEmail());
    organisationEntity.setPhoneNumber(organisationCreateRequest.getPhoneNumber());
    organisationEntity.setAddedBy(-1);
    organisationEntity.setUpdatedBy(-1);
    organisationRepo.save(organisationEntity);
    return StatusMessageResponse.builder()
        .data(organisationEntity)
        .code(HttpStatus.OK.value())
        .message("Organisation added successfully")
        .build();
  }

  @Override
  public StatusMessageResponse getAllOrganisations() throws Exception {
    List<OrganisationEntity> organisationEntityList = organisationRepo.findAll();
    if (CollectionUtils.isEmpty(organisationEntityList)) {
      organisationEntityList = new ArrayList<>();
    }
    return StatusMessageResponse.builder()
        .data(organisationEntityList)
        .code(HttpStatus.OK.value())
        .message("Organisation details fetched")
        .build();
  }

  @Override
  public StatusMessageResponse getOrganisationById(long id) throws Exception {
    Optional<OrganisationEntity> optionalOrganisationEntity = organisationRepo.findById(id);
    if (!optionalOrganisationEntity.isPresent()) {
      throw new ResourceNotFoundException("Organisation with id: " + id + " not found");
    }
    return StatusMessageResponse.builder()
        .data(optionalOrganisationEntity.get())
        .code(HttpStatus.OK.value())
        .message("Organisation details fetched")
        .build();
  }

  @Override
  public StatusMessageResponse deleteOrganisationById(long id) throws Exception {
    Optional<OrganisationEntity> optionalOrganisationEntity = organisationRepo.findById(id);
    if (!optionalOrganisationEntity.isPresent()) {
      throw new ResourceNotFoundException("Organisation with id: " + id + " not found");
    }
    OrganisationEntity organisationEntity = optionalOrganisationEntity.get();
    organisationEntity.setStatus(Constants.Status.IN_ACTIVE);
    organisationRepo.save(organisationEntity);
    return StatusMessageResponse.builder()
        .data(optionalOrganisationEntity.get())
        .code(HttpStatus.OK.value())
        .message("Organisation deleted successfully")
        .build();
  }

  @Override
  public StatusMessageResponse updateOrganisationById(
      long id, OrganisationUpdateRequest organisationUpdateRequest) throws Exception {
    Optional<OrganisationEntity> optionalOrganisationEntity = organisationRepo.findById(id);
    if (!optionalOrganisationEntity.isPresent()) {
      throw new ResourceNotFoundException("Organisation with id: " + id + " not found");
    }
    OrganisationEntity organisationEntity = optionalOrganisationEntity.get();
    organisationEntity.setName(organisationUpdateRequest.getName());
    organisationEntity.setEmail(organisationUpdateRequest.getEmail());
    organisationEntity.setPhoneNumber(organisationUpdateRequest.getPhoneNumber());
    organisationRepo.save(organisationEntity);
    return StatusMessageResponse.builder()
        .data(optionalOrganisationEntity.get())
        .code(HttpStatus.OK.value())
        .message("Organisation updated successfully")
        .build();
  }
}
