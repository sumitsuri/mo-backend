package com.Spring.SpringBootMysql.controller;

import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.internal.request.OrganisationCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.OrganisationUpdateRequest;
import com.Spring.SpringBootMysql.services.OrganisationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/organisations")
@Slf4j
public class OrganisationController {

  @Autowired private OrganisationService organisationService;

  @PostMapping
  public StatusMessageResponse createOrganisation(
      @RequestBody @Valid OrganisationCreateRequest organisationCreateRequest) throws Exception {
    return organisationService.addOrganisation(organisationCreateRequest);
  }

  @GetMapping
  public StatusMessageResponse getAllOrganisations() throws Exception {
    return organisationService.getAllOrganisations();
  }

  @GetMapping("/{id}")
  public StatusMessageResponse getOrganisation(@PathVariable long id) throws Exception {
    return organisationService.getOrganisationById(id);
  }

  @DeleteMapping("/{id}")
  public StatusMessageResponse removeOrganisation(@PathVariable long id) throws Exception {
    return organisationService.deleteOrganisationById(id);
  }

  @PutMapping("/{id}")
  public StatusMessageResponse updateOrganisation(
      @PathVariable long id,
      @RequestBody @Valid OrganisationUpdateRequest organisationUpdateRequest)
      throws Exception {
    return organisationService.updateOrganisationById(id, organisationUpdateRequest);
  }
}
