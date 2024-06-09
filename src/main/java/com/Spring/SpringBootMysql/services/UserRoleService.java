package com.Spring.SpringBootMysql.services;

import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.internal.request.UserRoleAddRequest;

public interface UserRoleService {
  StatusMessageResponse addUserRole(UserRoleAddRequest userRoleAddRequest) throws Exception;

  StatusMessageResponse getUserRoles(long userId) throws Exception;

  StatusMessageResponse removeUserRole(long id) throws Exception;

}
