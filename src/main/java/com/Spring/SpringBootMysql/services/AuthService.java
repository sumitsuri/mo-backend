package com.Spring.SpringBootMysql.services;

import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.dto.LoginRequest;
import com.Spring.SpringBootMysql.domains.internal.request.SignupRequest;
import com.Spring.SpringBootMysql.domains.internal.request.UserCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.UserUpdateRequest;

public interface AuthService {
  StatusMessageResponse login(LoginRequest loginRequest) throws Exception;
  StatusMessageResponse signUp(SignupRequest signupRequest) throws Exception;
}
