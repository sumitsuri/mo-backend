package com.Spring.SpringBootMysql.services;

import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.internal.request.OrganisationCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.OrganisationUpdateRequest;

public interface OrganisationService {
  StatusMessageResponse addOrganisation(OrganisationCreateRequest organisationCreateRequest)
      throws Exception;

  StatusMessageResponse getAllOrganisations() throws Exception;

  StatusMessageResponse getOrganisationById(long id) throws Exception;

  StatusMessageResponse deleteOrganisationById(long id) throws Exception;

  StatusMessageResponse updateOrganisationById(
      long id, OrganisationUpdateRequest organisationUpdateRequest) throws Exception;
}
