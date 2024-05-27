package com.Spring.SpringBootMysql.controller;

import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.internal.request.OrgLocationStoreCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.OrgLocationStoreUpdateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.ServiceCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.ServiceUpdateRequest;
import com.Spring.SpringBootMysql.services.OrgLocationStoreService;
import com.Spring.SpringBootMysql.services.OrgServicesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/services")
@Slf4j
public class OrgServicesController {

  @Autowired private OrgServicesService orgServicesService;

  @PostMapping
  public StatusMessageResponse createService(
      @RequestBody @Valid ServiceCreateRequest serviceCreateRequest) throws Exception {
    return orgServicesService.addService(serviceCreateRequest);
  }

  @GetMapping
  public StatusMessageResponse getAllServicesByCategory(@RequestParam(name = "serviceCategoryId") long serviceCategoryId) throws Exception {
    return orgServicesService.getAllServicesByCategoryId(serviceCategoryId);
  }

  @GetMapping("/{id}")
  public StatusMessageResponse getService(@PathVariable long id) throws Exception {
    return orgServicesService.getServiceById(id);
  }

  @DeleteMapping("/{id}")
  public StatusMessageResponse removeService(@PathVariable long id) throws Exception {
    return orgServicesService.deleteServiceById(id);
  }

  @PutMapping("/{id}")
  public StatusMessageResponse updateService(
      @PathVariable long id,
      @RequestBody @Valid ServiceUpdateRequest serviceUpdateRequest)
      throws Exception {
    return orgServicesService.updateServiceById(id, serviceUpdateRequest);
  }
}
