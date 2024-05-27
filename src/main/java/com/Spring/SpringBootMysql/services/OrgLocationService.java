package com.Spring.SpringBootMysql.services;

import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.internal.request.OrgLocationCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.OrgLocationUpdateRequest;

public interface OrgLocationService {
  StatusMessageResponse addOrgLocation(OrgLocationCreateRequest orgLocationCreateRequest)
      throws Exception;

  StatusMessageResponse getAllLocationsByOrgId(long orgId) throws Exception;

  StatusMessageResponse getOrgLocationById(long id) throws Exception;

  StatusMessageResponse deleteOrgLocationById(long id) throws Exception;

  StatusMessageResponse updateOrgLocationById(
      long id, OrgLocationUpdateRequest orgLocationUpdateRequest) throws Exception;
}
