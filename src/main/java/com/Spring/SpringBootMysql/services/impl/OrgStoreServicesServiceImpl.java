package com.Spring.SpringBootMysql.services.impl;

import com.Spring.SpringBootMysql.constants.Constants;
import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.internal.request.ServiceCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.ServiceUpdateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.StoreServiceCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.StoreServiceUpdateRequest;
import com.Spring.SpringBootMysql.entities.jpa.ServicesEntity;
import com.Spring.SpringBootMysql.entities.jpa.StoreServicesEntity;
import com.Spring.SpringBootMysql.exceptions.ResourceNotFoundException;
import com.Spring.SpringBootMysql.repository.ServicesRepo;
import com.Spring.SpringBootMysql.repository.StoreServicesRepo;
import com.Spring.SpringBootMysql.services.OrgServicesService;
import com.Spring.SpringBootMysql.services.OrgStoreServicesService;
import com.Spring.SpringBootMysql.utils.Utils;
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
public class OrgStoreServicesServiceImpl implements OrgStoreServicesService {

  StoreServicesRepo storeServicesRepo;
  Utils utils;

  @Autowired
  public OrgStoreServicesServiceImpl(StoreServicesRepo storeServicesRepo, Utils utils) {
    this.storeServicesRepo = storeServicesRepo;
    this.utils = utils;
  }

  @Transactional
  @Override
  public StatusMessageResponse addStoreService(StoreServiceCreateRequest storeServiceCreateRequest) throws Exception {
    StoreServicesEntity storeServicesEntity = new StoreServicesEntity();
    storeServicesEntity.setStoreId(storeServiceCreateRequest.getStoreId());
    storeServicesEntity.setServiceId(storeServiceCreateRequest.getServiceId());
    storeServicesEntity.setEstimatedTimeToComplete(storeServiceCreateRequest.getEstimatedTimeToComplete());
    storeServicesEntity.setPrice(storeServiceCreateRequest.getPrice());
    storeServicesEntity.setAddedBy(-1);
    storeServicesEntity.setUpdatedBy(-1);

    storeServicesRepo.save(storeServicesEntity);
    return StatusMessageResponse.builder()
            .data(storeServicesEntity)
            .code(HttpStatus.OK.value())
            .message("Store Service added successfully")
            .build();
  }

  @Override
  public StatusMessageResponse getAllStoreServices() throws Exception {
    List<StoreServicesEntity> storeServicesEntityList = storeServicesRepo.findAll();
    if (CollectionUtils.isEmpty(storeServicesEntityList)) {
      storeServicesEntityList = new ArrayList<>();
    }
    return StatusMessageResponse.builder()
            .data(storeServicesEntityList)
            .code(HttpStatus.OK.value())
            .message("Store Services details fetched")
            .build();
  }

  @Override
  public StatusMessageResponse getStoreServiceById(long id) throws Exception {
    Optional<StoreServicesEntity> optionalStoreServicesEntity = storeServicesRepo.findById(id);
    if (!optionalStoreServicesEntity.isPresent()) {
      throw new ResourceNotFoundException("Store Service with id: " + id + " not found");
    }
    return StatusMessageResponse.builder()
            .data(optionalStoreServicesEntity.get())
            .code(HttpStatus.OK.value())
            .message("Store Service fetched successfully")
            .build();
  }

  @Override
  public StatusMessageResponse getAllStoreServicesByStoreId(long storeId) throws Exception {
    Optional<List<StoreServicesEntity>> optionalStoreServices = storeServicesRepo.findByStoreId(storeId);
    List<StoreServicesEntity> services = new ArrayList<>();
    if (optionalStoreServices.isPresent()) {
      services = optionalStoreServices.get();
    }
    return StatusMessageResponse.builder()
            .data(services)
            .code(HttpStatus.OK.value())
            .message("Store Services details fetched")
            .build();
  }

  @Transactional
  @Override
  public StatusMessageResponse deleteStoreServiceById(long id) throws Exception {
    Optional<StoreServicesEntity> optionalStoreServicesEntity = storeServicesRepo.findById(id);
    if (!optionalStoreServicesEntity.isPresent()) {
      throw new ResourceNotFoundException("Store Service with id: " + id + " not found");
    }
    StoreServicesEntity storeServicesEntity = optionalStoreServicesEntity.get();
    storeServicesEntity.setStatus(Constants.Status.IN_ACTIVE);
    storeServicesRepo.save(storeServicesEntity);
    return StatusMessageResponse.builder()
            .data(storeServicesEntity)
            .code(HttpStatus.OK.value())
            .message("Store service deleted successfully")
            .build();

  }

  @Transactional
  @Override
  public StatusMessageResponse updateStoreServiceById(long id, StoreServiceUpdateRequest storeServiceUpdateRequest) throws Exception {
    Optional<StoreServicesEntity> optionalStoreServicesEntity = storeServicesRepo.findById(id);
    if (!optionalStoreServicesEntity.isPresent()) {
      throw new ResourceNotFoundException("Store Service with id: " + id + " not found");
    }
    StoreServicesEntity storeServicesEntity = optionalStoreServicesEntity.get();
    storeServicesEntity.setPrice(storeServiceUpdateRequest.getPrice());
    storeServicesEntity.setEstimatedTimeToComplete(storeServiceUpdateRequest.getEstimatedTimeToComplete());
    storeServicesRepo.save(storeServicesEntity);
    return StatusMessageResponse.builder()
            .data(storeServicesEntity)
            .code(HttpStatus.OK.value())
            .message("Store service updated successfully")
            .build();
  }
}
