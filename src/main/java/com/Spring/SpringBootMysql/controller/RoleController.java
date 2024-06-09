package com.Spring.SpringBootMysql.controller;

import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.internal.request.*;
import com.Spring.SpringBootMysql.services.RoleService;
import com.Spring.SpringBootMysql.services.UserMetadataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/roles")
@Slf4j
public class RoleController {

  @Autowired private RoleService roleService;

  @PostMapping
  public StatusMessageResponse createRole(
      @RequestBody @Valid RoleCreateRequest roleCreateRequest) throws Exception {
    return roleService.addRole(roleCreateRequest);
  }

  @GetMapping
  public StatusMessageResponse getAllRoles() throws Exception {
    return roleService.getAllRoles();
  }

  @GetMapping("/{id}")
  public StatusMessageResponse getRole(@PathVariable long id) throws Exception {
    return roleService.getRoleById(id);
  }

  @DeleteMapping("/{id}")
  public StatusMessageResponse removeRole(@PathVariable long id) throws Exception {
    return roleService.deleteRoleById(id);
  }

}
