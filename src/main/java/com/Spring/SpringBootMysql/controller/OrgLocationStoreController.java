package com.Spring.SpringBootMysql.controller;

import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.internal.request.OrgLocationCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.OrgLocationStoreCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.OrgLocationStoreUpdateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.OrgLocationUpdateRequest;
import com.Spring.SpringBootMysql.services.OrgLocationStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/stores")
@Slf4j
public class OrgLocationStoreController {

  @Autowired private OrgLocationStoreService orgLocationStoreService;

  @PostMapping
  public StatusMessageResponse createStore(
      @RequestBody @Valid OrgLocationStoreCreateRequest orgLocationStoreCreateRequest) throws Exception {
    return orgLocationStoreService.addLocationStore(orgLocationStoreCreateRequest);
  }

  @GetMapping
  public StatusMessageResponse getAllStoresByLocation(@RequestParam(name = "locId") long orgLocationId) throws Exception {
    return orgLocationStoreService.getAllStoreByLocationId(orgLocationId);
  }

  @GetMapping("/{id}")
  public StatusMessageResponse getStore(@PathVariable long id) throws Exception {
    return orgLocationStoreService.getOrgLocationStoreById(id);
  }

  @DeleteMapping("/{id}")
  public StatusMessageResponse removeStore(@PathVariable long id) throws Exception {
    return orgLocationStoreService.deleteOrgLocationStoreById(id);
  }

  @PutMapping("/{id}")
  public StatusMessageResponse updateStore(
      @PathVariable long id,
      @RequestBody @Valid OrgLocationStoreUpdateRequest orgLocationStoreUpdateRequest)
      throws Exception {
    return orgLocationStoreService.updateOrgLocationStoreById(id,orgLocationStoreUpdateRequest);
  }
}
