package com.Spring.SpringBootMysql.controller;

import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.dto.LoginRequest;
import com.Spring.SpringBootMysql.domains.internal.request.*;
import com.Spring.SpringBootMysql.services.UserMetadataService;
import com.Spring.SpringBootMysql.services.UserRoleService;
import com.Spring.SpringBootMysql.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/users")
@Slf4j
public class UserController {

  @Autowired private UserService userService;
  @Autowired private UserMetadataService userMetadataService;

  @Autowired private UserRoleService userRoleService;

  @PostMapping
  public StatusMessageResponse createUser(
      @RequestBody @Valid UserCreateRequest userCreateRequest) throws Exception {
    return userService.addUser(userCreateRequest);
  }


  @GetMapping
  public StatusMessageResponse getAllUsers() throws Exception {
    return userService.getAllUsers();
  }

  @GetMapping("/{id}")
  public StatusMessageResponse getUser(@PathVariable long id) throws Exception {
    return userService.getUserById(id);
  }

  @DeleteMapping("/{id}")
  public StatusMessageResponse removeUser(@PathVariable long id) throws Exception {
    return userService.deleteUserById(id);
  }

  @PutMapping("/{id}")
  public StatusMessageResponse updateUser(
      @PathVariable long id, @RequestBody @Valid UserUpdateRequest userUpdateRequest)
      throws Exception {
    return userService.updateUserById(id, userUpdateRequest);
  }

  @PostMapping("/{id}/user-metadata")
  public StatusMessageResponse addUserMetadata(
      @PathVariable long id,
      @RequestBody @Valid UserMetadataCreateRequest userMetadataCreateRequest)
      throws Exception {
    return userMetadataService.addUserMetadata(userMetadataCreateRequest);
  }

  @GetMapping("/{id}/user-metadata")
  public StatusMessageResponse getUserMetadataByUserId(@PathVariable long id) throws Exception {
    return userMetadataService.getUserMetadataByUserId(id);
  }

  @PutMapping("/{id}/user-metadata/{metadataId}")
  public StatusMessageResponse updateUserMetadata(
      @PathVariable long id,
      @PathVariable long metadataId,
      @RequestBody @Valid UserMetadataUpdateRequest userMetadataUpdateRequest)
      throws Exception {
    return userMetadataService.updateUserMetadataById(metadataId, userMetadataUpdateRequest);
  }

  @PostMapping("/{id}/user-roles")
  public StatusMessageResponse addUserRole(
          @PathVariable long id,
          @RequestBody @Valid UserRoleAddRequest userRoleAddRequest)
          throws Exception {
    return userRoleService.addUserRole(userRoleAddRequest);
  }

  @GetMapping("/{id}/user-roles")
  StatusMessageResponse getUserRoles(long userId) throws Exception {
    return userRoleService.getUserRoles(userId);
  }

  @DeleteMapping("/{id}/user-roles/{userRoleId}")
  public StatusMessageResponse removeUserRole(@PathVariable long userRoleId) throws Exception {
    return userRoleService.removeUserRole(userRoleId);
  }

}
