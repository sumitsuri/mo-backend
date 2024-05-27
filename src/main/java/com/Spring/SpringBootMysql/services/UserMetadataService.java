package com.Spring.SpringBootMysql.services;

import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.internal.request.UserMetadataCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.UserMetadataUpdateRequest;

public interface UserMetadataService {
  StatusMessageResponse addUserMetadata(UserMetadataCreateRequest userMetadataCreateRequest)
      throws Exception;

  StatusMessageResponse getUserMetadataByUserId(long userId) throws Exception;

  StatusMessageResponse updateUserMetadataById(
      long id, UserMetadataUpdateRequest userMetadataUpdateRequest) throws Exception;
}
