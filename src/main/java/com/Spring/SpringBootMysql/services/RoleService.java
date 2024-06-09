package com.Spring.SpringBootMysql.services;

import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.internal.request.RoleCreateRequest;

public interface RoleService {
  StatusMessageResponse addRole(RoleCreateRequest roleCreateRequest) throws Exception;

  StatusMessageResponse getAllRoles() throws Exception;

  StatusMessageResponse getRoleById(long id) throws Exception;

  StatusMessageResponse deleteRoleById(long id) throws Exception;
}
