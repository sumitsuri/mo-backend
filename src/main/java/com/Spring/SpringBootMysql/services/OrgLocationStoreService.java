package com.Spring.SpringBootMysql.services;

import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.internal.request.OrgLocationCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.OrgLocationStoreCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.OrgLocationStoreUpdateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.OrgLocationUpdateRequest;

public interface OrgLocationStoreService {
  StatusMessageResponse addLocationStore(OrgLocationStoreCreateRequest orgLocationStoreCreateRequest)
      throws Exception;

  StatusMessageResponse getAllStoreByLocationId(long orgLocationId) throws Exception;

  StatusMessageResponse getOrgLocationStoreById(long id) throws Exception;

  StatusMessageResponse deleteOrgLocationStoreById(long id) throws Exception;

  StatusMessageResponse updateOrgLocationStoreById(
      long id, OrgLocationStoreUpdateRequest orgLocationStoreUpdateRequest) throws Exception;
}
