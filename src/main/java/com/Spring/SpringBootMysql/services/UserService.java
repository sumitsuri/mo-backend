package com.Spring.SpringBootMysql.services;

import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.internal.request.UserCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.UserUpdateRequest;

public interface UserService {
  StatusMessageResponse addUser(UserCreateRequest userCreateRequest) throws Exception;

  StatusMessageResponse getAllUsers() throws Exception;

  StatusMessageResponse getUserById(long id) throws Exception;

  StatusMessageResponse deleteUserById(long id) throws Exception;

  StatusMessageResponse updateUserById(long id, UserUpdateRequest userUpdateRequest)
      throws Exception;

  StatusMessageResponse getUserByEmail(String email);

  boolean hasUserWithEmail(String email);
  boolean hasUserWithPhoneNumber(String phoneNumber);
}
