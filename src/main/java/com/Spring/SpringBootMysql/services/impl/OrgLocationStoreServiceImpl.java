package com.Spring.SpringBootMysql.services.impl;

import com.Spring.SpringBootMysql.constants.Constants;
import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.internal.request.OrgLocationCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.OrgLocationStoreCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.OrgLocationStoreUpdateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.OrgLocationUpdateRequest;
import com.Spring.SpringBootMysql.entities.jpa.OrgLocationEntity;
import com.Spring.SpringBootMysql.entities.jpa.OrgLocationStoreEntity;
import com.Spring.SpringBootMysql.exceptions.ResourceNotFoundException;
import com.Spring.SpringBootMysql.repository.OrgLocationRepo;
import com.Spring.SpringBootMysql.repository.OrgLocationStoreRepo;
import com.Spring.SpringBootMysql.repository.OrganisationRepo;
import com.Spring.SpringBootMysql.services.OrgLocationService;
import com.Spring.SpringBootMysql.services.OrgLocationStoreService;
import com.Spring.SpringBootMysql.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class OrgLocationStoreServiceImpl implements OrgLocationStoreService {

  OrgLocationStoreRepo orgLocationStoreRepo;
  OrgLocationRepo orgLocationRepo;
  Utils utils;

  @Autowired
  public OrgLocationStoreServiceImpl(OrgLocationStoreRepo orgLocationStoreRepo, Utils utils) {
    this.orgLocationStoreRepo = orgLocationStoreRepo;
    this.utils = utils;
  }


  @Transactional
  @Override
  public StatusMessageResponse addLocationStore(OrgLocationStoreCreateRequest orgLocationStoreCreateRequest) throws Exception {

    OrgLocationStoreEntity orgLocationStoreEntity = new OrgLocationStoreEntity();
    orgLocationStoreEntity.setStoreIdentifier(orgLocationStoreCreateRequest.getStoreIdentifier());
    orgLocationStoreEntity.setName(orgLocationStoreCreateRequest.getName());
    orgLocationStoreEntity.setType(orgLocationStoreCreateRequest.getType());
    orgLocationStoreEntity.setEmail(orgLocationStoreCreateRequest.getEmail());
    orgLocationStoreEntity.setPhoneNumber(orgLocationStoreCreateRequest.getPhoneNumber());
    orgLocationStoreEntity.setOrgLocationId(orgLocationStoreCreateRequest.getOrgLocationId());

    orgLocationStoreEntity.setAddedBy(-1);
    orgLocationStoreEntity.setUpdatedBy(-1);
    orgLocationStoreRepo.save(orgLocationStoreEntity);
    return StatusMessageResponse.builder()
            .data(orgLocationStoreEntity)
            .code(HttpStatus.OK.value())
            .message("Store added successfully")
            .build();
  }

  @Override
  public StatusMessageResponse getAllStoreByLocationId(long orgLocationId) throws Exception {
    Optional<List<OrgLocationStoreEntity>> optionalOrgLocationStores = orgLocationStoreRepo.findByOrgLocationId(orgLocationId);
    List<OrgLocationStoreEntity> orgLocationStores = new ArrayList<>();
    if (optionalOrgLocationStores.isPresent()) {
      orgLocationStores = optionalOrgLocationStores.get();
    }
    return StatusMessageResponse.builder()
            .data(orgLocationStores)
            .code(HttpStatus.OK.value())
            .message("Location stores details fetched")
            .build();
  }

  @Override
  public StatusMessageResponse getOrgLocationStoreById(long id) throws Exception {
    Optional<OrgLocationStoreEntity> optionalOrgLocationStoreEntity = orgLocationStoreRepo.findById(id);
    if (!optionalOrgLocationStoreEntity.isPresent()) {
      throw new ResourceNotFoundException("Store with id: " + id + " not found");
    }
    return StatusMessageResponse.builder()
            .data(optionalOrgLocationStoreEntity.get())
            .code(HttpStatus.OK.value())
            .message("Store fetched successfully")
            .build();
  }

  @Transactional
  @Override
  public StatusMessageResponse deleteOrgLocationStoreById(long id) throws Exception {
    Optional<OrgLocationStoreEntity> optionalOrgLocationStoreEntity = orgLocationStoreRepo.findById(id);
    if (!optionalOrgLocationStoreEntity.isPresent()) {
      throw new ResourceNotFoundException("Store with id: " + id + " not found");
    }
    OrgLocationStoreEntity orgLocationStoreEntity = optionalOrgLocationStoreEntity.get();
    orgLocationStoreEntity.setStatus(Constants.Status.IN_ACTIVE);

    orgLocationStoreRepo.save(orgLocationStoreEntity);

    return StatusMessageResponse.builder()
            .data(orgLocationStoreEntity)
            .code(HttpStatus.OK.value())
            .message("Store deleted successfully")
            .build();
  }

  @Transactional
  @Override
  public StatusMessageResponse updateOrgLocationStoreById(long id, OrgLocationStoreUpdateRequest orgLocationStoreUpdateRequest) throws Exception {
    Optional<OrgLocationStoreEntity> optionalOrgLocationStoreEntity = orgLocationStoreRepo.findById(id);
    if (!optionalOrgLocationStoreEntity.isPresent()) {
      throw new ResourceNotFoundException("Store with id: " + id + " not found");
    }
    OrgLocationStoreEntity orgLocationStoreEntity = optionalOrgLocationStoreEntity.get();
    orgLocationStoreEntity.setPhoneNumber(orgLocationStoreUpdateRequest.getPhoneNumber());
    orgLocationStoreEntity.setName(orgLocationStoreUpdateRequest.getName());
    orgLocationStoreEntity.setEmail(orgLocationStoreUpdateRequest.getEmail());

    orgLocationStoreRepo.save(orgLocationStoreEntity);

    return StatusMessageResponse.builder()
            .data(orgLocationStoreEntity)
            .code(HttpStatus.OK.value())
            .message("Store updated successfully")
            .build();
  }
}
