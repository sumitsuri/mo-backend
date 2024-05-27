package com.Spring.SpringBootMysql.services;

import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.internal.request.ServiceCategoryCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.ServiceCategoryUpdateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.ServiceCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.ServiceUpdateRequest;

public interface OrgServicesService {
  StatusMessageResponse addService(ServiceCreateRequest serviceCreateRequest)
      throws Exception;

  StatusMessageResponse getAllServices() throws Exception;

  StatusMessageResponse getServiceById(long id) throws Exception;

  StatusMessageResponse getAllServicesByCategoryId(long serviceCategoryId) throws Exception;

  StatusMessageResponse deleteServiceById(long id) throws Exception;

  StatusMessageResponse updateServiceById(
      long id, ServiceUpdateRequest serviceUpdateRequest) throws Exception;
}
