package com.Spring.SpringBootMysql.controller;

import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.internal.request.OrgLocationCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.OrgLocationUpdateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.OrganisationCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.OrganisationUpdateRequest;
import com.Spring.SpringBootMysql.services.OrgLocationService;
import com.Spring.SpringBootMysql.services.OrganisationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/org-locations")
@Slf4j
public class OrgLocationController {

  @Autowired private OrgLocationService orgLocationService;

  @PostMapping
  public StatusMessageResponse createOrgLocation(
      @RequestBody @Valid OrgLocationCreateRequest organisationCreateRequest) throws Exception {
    return orgLocationService.addOrgLocation(organisationCreateRequest);
  }

  @GetMapping
  public StatusMessageResponse getAllOrgLocationsByOrg(@RequestParam(name = "orgId")long orgId) throws Exception {
    return orgLocationService.getAllLocationsByOrgId(orgId);
  }

  @GetMapping("/{id}")
  public StatusMessageResponse getOrgLocation(@PathVariable long id) throws Exception {
    return orgLocationService.getOrgLocationById(id);
  }

  @DeleteMapping("/{id}")
  public StatusMessageResponse removeOrganisation(@PathVariable long id) throws Exception {
    return orgLocationService.deleteOrgLocationById(id);
  }

  @PutMapping("/{id}")
  public StatusMessageResponse updateOrgLocation(
      @PathVariable long id,
      @RequestBody @Valid OrgLocationUpdateRequest organisationUpdateRequest)
      throws Exception {
    return orgLocationService.updateOrgLocationById(id, organisationUpdateRequest);
  }
}
