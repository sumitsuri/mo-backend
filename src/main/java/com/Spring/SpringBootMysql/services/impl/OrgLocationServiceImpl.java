package com.Spring.SpringBootMysql.services.impl;

import com.Spring.SpringBootMysql.constants.Constants;
import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.internal.request.OrganisationCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.OrgLocationCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.OrgLocationUpdateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.OrganisationUpdateRequest;
import com.Spring.SpringBootMysql.entities.jpa.OrgLocationEntity;
import com.Spring.SpringBootMysql.entities.jpa.OrganisationEntity;
import com.Spring.SpringBootMysql.exceptions.ResourceNotFoundException;
import com.Spring.SpringBootMysql.repository.OrgLocationRepo;
import com.Spring.SpringBootMysql.repository.OrganisationRepo;
import com.Spring.SpringBootMysql.services.OrgLocationService;
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
public class OrgLocationServiceImpl implements OrgLocationService {

  OrganisationRepo organisationRepo;
  OrgLocationRepo orgLocationRepo;
  Utils utils;

  @Autowired
  public OrgLocationServiceImpl(OrgLocationRepo orgLocationRepo, Utils utils) {
    this.orgLocationRepo = orgLocationRepo;
    this.utils = utils;
  }

  @Transactional
  @Override
  public StatusMessageResponse addOrgLocation(OrgLocationCreateRequest orgLocationCreateRequest) throws Exception {
    OrgLocationEntity orgLocationEntity = new OrgLocationEntity();
    orgLocationEntity.setOrgId(orgLocationCreateRequest.getOrgId());
    orgLocationEntity.setCity(orgLocationCreateRequest.getCity());
    orgLocationEntity.setState(orgLocationCreateRequest.getState());
    orgLocationEntity.setPincode(orgLocationCreateRequest.getPincode());
    orgLocationEntity.setAddress(orgLocationCreateRequest.getAddress());
    orgLocationEntity.setPhoneNumber(orgLocationCreateRequest.getPhoneNumber());
    orgLocationEntity.setAddedBy(-1);
    orgLocationEntity.setUpdatedBy(-1);
    orgLocationRepo.save(orgLocationEntity);
    return StatusMessageResponse.builder()
            .data(orgLocationEntity)
            .code(HttpStatus.OK.value())
            .message("Organisation Location added successfully")
            .build();
  }

  @Override
  public StatusMessageResponse getAllLocationsByOrgId(long orgId) throws Exception {
    Optional<List<OrgLocationEntity>> optionalOrgLocations = orgLocationRepo.findByOrgId(orgId);
    List<OrgLocationEntity> orgLocations = new ArrayList<>();
    if (optionalOrgLocations.isPresent()) {
      orgLocations = optionalOrgLocations.get();
    }
    return StatusMessageResponse.builder()
            .data(orgLocations)
            .code(HttpStatus.OK.value())
            .message("Org Locations details fetched")
            .build();
  }

  @Override
  public StatusMessageResponse getOrgLocationById(long id) throws Exception {
    Optional<OrgLocationEntity> optionalOrgLocationEntity = orgLocationRepo.findById(id);
    if (!optionalOrgLocationEntity.isPresent()) {
      throw new ResourceNotFoundException("Organisation Location with id: " + id + " not found");
    }
    return StatusMessageResponse.builder()
            .data(optionalOrgLocationEntity.get())
            .code(HttpStatus.OK.value())
            .message("Organisation Location details fetched")
            .build();
  }

  @Override
  public StatusMessageResponse deleteOrgLocationById(long id) throws Exception {
    Optional<OrgLocationEntity> optionalOrgLocationEntity = orgLocationRepo.findById(id);
    if (!optionalOrgLocationEntity.isPresent()) {
      throw new ResourceNotFoundException("Organisation Location with id: " + id + " not found");
    }
    OrgLocationEntity orgLocationEntity = optionalOrgLocationEntity.get();
    orgLocationEntity.setStatus(Constants.Status.IN_ACTIVE);
    orgLocationRepo.save(orgLocationEntity);
    return StatusMessageResponse.builder()
            .data(optionalOrgLocationEntity.get())
            .code(HttpStatus.OK.value())
            .message("Organisation Location deleted successfully")
            .build();
  }

  @Override
  public StatusMessageResponse updateOrgLocationById(long id, OrgLocationUpdateRequest orgLocationUpdateRequest) throws Exception {
    Optional<OrgLocationEntity> optionalOrgLocationEntity = orgLocationRepo.findById(id);
    if (!optionalOrgLocationEntity.isPresent()) {
      throw new ResourceNotFoundException("Organisation Location with id: " + id + " not found");
    }
    OrgLocationEntity orgLocationEntity = optionalOrgLocationEntity.get();
    orgLocationEntity.setPhoneNumber(orgLocationUpdateRequest.getPhoneNumber());
    orgLocationRepo.save(orgLocationEntity);
    return StatusMessageResponse.builder()
            .data(orgLocationEntity)
            .code(HttpStatus.OK.value())
            .message("Organisation Location updated successfully")
            .build();
  }
}
