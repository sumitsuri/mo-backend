package com.Spring.SpringBootMysql.services.impl;

import com.Spring.SpringBootMysql.constants.Constants;
import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.internal.request.OrgLocationStoreCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.OrgLocationStoreUpdateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.ServiceCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.ServiceUpdateRequest;
import com.Spring.SpringBootMysql.entities.jpa.OrgLocationStoreEntity;
import com.Spring.SpringBootMysql.entities.jpa.OrganisationEntity;
import com.Spring.SpringBootMysql.entities.jpa.ServicesEntity;
import com.Spring.SpringBootMysql.exceptions.ResourceNotFoundException;
import com.Spring.SpringBootMysql.repository.OrgLocationStoreRepo;
import com.Spring.SpringBootMysql.repository.ServicesRepo;
import com.Spring.SpringBootMysql.services.OrgServicesService;
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
public class OrgServicesServiceImpl implements OrgServicesService {

  ServicesRepo servicesRepo;
  Utils utils;

  @Autowired
  public OrgServicesServiceImpl(ServicesRepo servicesRepo, Utils utils) {
    this.servicesRepo = servicesRepo;
    this.utils = utils;
  }


  @Transactional
  @Override
  public StatusMessageResponse addService(ServiceCreateRequest serviceCreateRequest) throws Exception {
    ServicesEntity servicesEntity = new ServicesEntity();
    servicesEntity.setName(serviceCreateRequest.getName());
    servicesEntity.setDescription(serviceCreateRequest.getDescription());
    servicesEntity.setImage(serviceCreateRequest.getImage());
    servicesEntity.setEstimatedTimeToComplete(serviceCreateRequest.getEstimatedTimeToComplete());
    servicesEntity.setServiceCategoryId(serviceCreateRequest.getServiceCategoryId());
    servicesEntity.setPrice(serviceCreateRequest.getPrice());
    servicesEntity.setAddedBy(-1);
    servicesEntity.setUpdatedBy(-1);

    servicesRepo.save(servicesEntity);
    return StatusMessageResponse.builder()
            .data(servicesEntity)
            .code(HttpStatus.OK.value())
            .message("Service added successfully")
            .build();
  }

  @Override
  public StatusMessageResponse getAllServices() throws Exception {
    List<ServicesEntity> servicesEntityList = servicesRepo.findAll();
    if (CollectionUtils.isEmpty(servicesEntityList)) {
      servicesEntityList = new ArrayList<>();
    }
    return StatusMessageResponse.builder()
            .data(servicesEntityList)
            .code(HttpStatus.OK.value())
            .message("Services details fetched")
            .build();
  }

  @Override
  public StatusMessageResponse getServiceById(long id) throws Exception {
    Optional<ServicesEntity> optionalServicesEntity = servicesRepo.findById(id);
    if (!optionalServicesEntity.isPresent()) {
      throw new ResourceNotFoundException("Service with id: " + id + " not found");
    }
    return StatusMessageResponse.builder()
            .data(optionalServicesEntity.get())
            .code(HttpStatus.OK.value())
            .message("Service fetched successfully")
            .build();
  }

  @Override
  public StatusMessageResponse getAllServicesByCategoryId(long serviceCategoryId) throws Exception {
    Optional<List<ServicesEntity>> optionalServices = servicesRepo.findByServiceCategoryId(serviceCategoryId);
    List<ServicesEntity> services = new ArrayList<>();
    if (optionalServices.isPresent()) {
      services = optionalServices.get();
    }
    return StatusMessageResponse.builder()
            .data(services)
            .code(HttpStatus.OK.value())
            .message("Services details fetched")
            .build();
  }

  @Transactional
  @Override
  public StatusMessageResponse deleteServiceById(long id) throws Exception {
    Optional<ServicesEntity> optionalServiceEntity = servicesRepo.findById(id);
    if (!optionalServiceEntity.isPresent()) {
      throw new ResourceNotFoundException("Service with id: " + id + " not found");
    }
    ServicesEntity serviceEntity = optionalServiceEntity.get();
    serviceEntity.setStatus(Constants.Status.IN_ACTIVE);

    servicesRepo.save(serviceEntity);

    return StatusMessageResponse.builder()
            .data(serviceEntity)
            .code(HttpStatus.OK.value())
            .message("Service deleted successfully")
            .build();
  }

  @Transactional
  @Override
  public StatusMessageResponse updateServiceById(long id, ServiceUpdateRequest serviceUpdateRequest) throws Exception {
    Optional<ServicesEntity> optionalServiceEntity = servicesRepo.findById(id);
    if (!optionalServiceEntity.isPresent()) {
      throw new ResourceNotFoundException("Service with id: " + id + " not found");
    }
    ServicesEntity serviceEntity = optionalServiceEntity.get();
    serviceEntity.setName(serviceUpdateRequest.getName());
    serviceEntity.setDescription(serviceUpdateRequest.getDescription());
    serviceEntity.setImage(serviceUpdateRequest.getImage());
    serviceEntity.setEstimatedTimeToComplete(serviceUpdateRequest.getEstimatedTimeToComplete());
    serviceEntity.setServiceCategoryId(serviceUpdateRequest.getServiceCategoryId());
    serviceEntity.setPrice(serviceUpdateRequest.getPrice());

    servicesRepo.save(serviceEntity);

    return StatusMessageResponse.builder()
            .data(serviceEntity)
            .code(HttpStatus.OK.value())
            .message("Store updated successfully")
            .build();
  }
}
