package com.Spring.SpringBootMysql.services;

import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.internal.request.ServiceCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.ServiceUpdateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.StoreServiceCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.StoreServiceUpdateRequest;

public interface OrgStoreServicesService {
  StatusMessageResponse addStoreService(StoreServiceCreateRequest storeServiceCreateRequest)
      throws Exception;

  StatusMessageResponse getAllStoreServices() throws Exception;

  StatusMessageResponse getStoreServiceById(long id) throws Exception;

  StatusMessageResponse getAllStoreServicesByStoreId(long storeId) throws Exception;

  StatusMessageResponse deleteStoreServiceById(long id) throws Exception;

  StatusMessageResponse updateStoreServiceById(
          long id, StoreServiceUpdateRequest storeServiceUpdateRequest) throws Exception;
}
