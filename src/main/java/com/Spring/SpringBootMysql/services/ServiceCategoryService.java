package com.Spring.SpringBootMysql.services;

import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.internal.request.OrganisationCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.OrganisationUpdateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.ServiceCategoryCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.ServiceCategoryUpdateRequest;

public interface ServiceCategoryService {
  StatusMessageResponse addServiceCategory(ServiceCategoryCreateRequest serviceCategoryCreateRequest)
      throws Exception;

  StatusMessageResponse getAllServiceCategories() throws Exception;

  StatusMessageResponse getServiceCategoryById(long id) throws Exception;

  StatusMessageResponse updateServiceCategoryById(
      long id, ServiceCategoryUpdateRequest serviceCategoryUpdateRequest) throws Exception;
}
