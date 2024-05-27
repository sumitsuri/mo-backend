package com.Spring.SpringBootMysql.controller;

import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.internal.request.OrganisationCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.OrganisationUpdateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.ServiceCategoryCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.ServiceCategoryUpdateRequest;
import com.Spring.SpringBootMysql.services.OrganisationService;
import com.Spring.SpringBootMysql.services.ServiceCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/service-categories")
@Slf4j
public class ServiceCategoryController {

  @Autowired private ServiceCategoryService serviceCategoryService;

  @PostMapping
  public StatusMessageResponse createServiceCategory(
      @RequestBody @Valid ServiceCategoryCreateRequest serviceCategoryCreateRequest) throws Exception {
    return serviceCategoryService.addServiceCategory(serviceCategoryCreateRequest);
  }

  @GetMapping
  public StatusMessageResponse getAllServiceCategories() throws Exception {
    return serviceCategoryService.getAllServiceCategories();
  }

  @GetMapping("/{id}")
  public StatusMessageResponse getServiceCategory(@PathVariable long id) throws Exception {
    return serviceCategoryService.getServiceCategoryById(id);
  }

  @PutMapping("/{id}")
  public StatusMessageResponse updateServiceCategory(
      @PathVariable long id,
      @RequestBody @Valid ServiceCategoryUpdateRequest serviceCategoryUpdateRequest)
      throws Exception {
    return serviceCategoryService.updateServiceCategoryById(id, serviceCategoryUpdateRequest);
  }
}
