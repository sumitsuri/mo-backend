package com.Spring.SpringBootMysql.controller;

import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.internal.request.ServiceCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.ServiceUpdateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.StoreServiceCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.StoreServiceUpdateRequest;
import com.Spring.SpringBootMysql.services.OrgServicesService;
import com.Spring.SpringBootMysql.services.OrgStoreServicesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/store-services")
@Slf4j
public class OrgStoreServicesController {

  @Autowired private OrgStoreServicesService orgStoreServicesService;

  @PostMapping
  public StatusMessageResponse createService(
      @RequestBody @Valid StoreServiceCreateRequest storeServiceCreateRequest) throws Exception {
    return orgStoreServicesService.addStoreService(storeServiceCreateRequest);
  }

  @GetMapping
  public StatusMessageResponse getAllServicesByStore(@RequestParam(name = "storeId") long storeId) throws Exception {
    return orgStoreServicesService.getAllStoreServicesByStoreId(storeId);
  }

  @GetMapping("/{id}")
  public StatusMessageResponse getService(@PathVariable long id) throws Exception {
    return orgStoreServicesService.getStoreServiceById(id);
  }

  @DeleteMapping("/{id}")
  public StatusMessageResponse removeService(@PathVariable long id) throws Exception {
    return orgStoreServicesService.deleteStoreServiceById(id);
  }

  @PutMapping("/{id}")
  public StatusMessageResponse updateService(
      @PathVariable long id,
      @RequestBody @Valid StoreServiceUpdateRequest storeServiceUpdateRequest)
      throws Exception {
    return orgStoreServicesService.updateStoreServiceById(id, storeServiceUpdateRequest);
  }
}
