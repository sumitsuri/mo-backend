package com.Spring.SpringBootMysql.controller;

import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.internal.request.UserCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.UserMetadataCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.UserMetadataUpdateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.UserUpdateRequest;
import com.Spring.SpringBootMysql.services.UserMetadataService;
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
}
