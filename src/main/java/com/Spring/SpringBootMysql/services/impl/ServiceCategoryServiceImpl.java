package com.Spring.SpringBootMysql.services.impl;

import com.Spring.SpringBootMysql.constants.Constants;
import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.internal.request.OrganisationCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.OrganisationUpdateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.ServiceCategoryCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.ServiceCategoryUpdateRequest;
import com.Spring.SpringBootMysql.entities.jpa.OrganisationEntity;
import com.Spring.SpringBootMysql.entities.jpa.ServiceCategoryEntity;
import com.Spring.SpringBootMysql.exceptions.ResourceNotFoundException;
import com.Spring.SpringBootMysql.repository.OrganisationRepo;
import com.Spring.SpringBootMysql.repository.ServiceCategoryRepo;
import com.Spring.SpringBootMysql.services.OrganisationService;
import com.Spring.SpringBootMysql.services.ServiceCategoryService;
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
public class ServiceCategoryServiceImpl implements ServiceCategoryService {

  //  PaginatedMerchantRepo paginatedMerchantRepo;

  private static final ObjectMapper objectMapper =
      new ObjectMapper().registerModule(new JavaTimeModule());
  OrganisationRepo organisationRepo;
  ServiceCategoryRepo serviceCategoryRepo;
  Utils utils;

  @Autowired
  public ServiceCategoryServiceImpl(ServiceCategoryRepo serviceCategoryRepo, Utils utils) {
    this.serviceCategoryRepo = serviceCategoryRepo;
    this.utils = utils;
  }

  @Transactional
  @Override
  public StatusMessageResponse addServiceCategory(ServiceCategoryCreateRequest serviceCategoryCreateRequest) throws Exception {
    ServiceCategoryEntity serviceCategoryEntity = new ServiceCategoryEntity();
    serviceCategoryEntity.setName(serviceCategoryCreateRequest.getName());
    serviceCategoryEntity.setDescription(serviceCategoryCreateRequest.getDescription());
    serviceCategoryEntity.setCategoryId(serviceCategoryCreateRequest.getCategoryId());
    serviceCategoryEntity.setAddedBy(-1);
    serviceCategoryEntity.setUpdatedBy(-1);
    serviceCategoryRepo.save(serviceCategoryEntity);
    return StatusMessageResponse.builder()
            .data(serviceCategoryEntity)
            .code(HttpStatus.OK.value())
            .message("Service Category added successfully")
            .build();
  }

  @Override
  public StatusMessageResponse getAllServiceCategories() throws Exception {
    List<ServiceCategoryEntity> servicecategories = serviceCategoryRepo.findAll();
    if (CollectionUtils.isEmpty(servicecategories)) {
      servicecategories = new ArrayList<>();
    }
    return StatusMessageResponse.builder()
            .data(servicecategories)
            .code(HttpStatus.OK.value())
            .message("Service categories details fetched")
            .build();
  }

  @Override
  public StatusMessageResponse getServiceCategoryById(long id) throws Exception {
    Optional<ServiceCategoryEntity> optionalServicecategoryEntity = serviceCategoryRepo.findById(id);
    if (!optionalServicecategoryEntity.isPresent()) {
      throw new ResourceNotFoundException("Service Category with id: " + id + " not found");
    }
    return StatusMessageResponse.builder()
            .data(optionalServicecategoryEntity.get())
            .code(HttpStatus.OK.value())
            .message("Service categories details fetched")
            .build();
  }

  @Transactional
  @Override
  public StatusMessageResponse updateServiceCategoryById(long id, ServiceCategoryUpdateRequest serviceCategoryUpdateRequest) throws Exception {
    Optional<ServiceCategoryEntity> optionalServiceCategoryEntity = serviceCategoryRepo.findById(id);
    if (!optionalServiceCategoryEntity.isPresent()) {
      throw new ResourceNotFoundException("Service Category with id: " + id + " not found");
    }
    ServiceCategoryEntity serviceCategoryEntity = optionalServiceCategoryEntity.get();
    serviceCategoryEntity.setName(serviceCategoryUpdateRequest.getName());
    serviceCategoryEntity.setDescription(serviceCategoryUpdateRequest.getDescription());
    serviceCategoryEntity.setCategoryId(serviceCategoryUpdateRequest.getCategoryId());

    serviceCategoryRepo.save(serviceCategoryEntity);
    return StatusMessageResponse.builder()
            .data(serviceCategoryEntity)
            .code(HttpStatus.OK.value())
            .message("Organisation updated successfully")
            .build();
  }
}
