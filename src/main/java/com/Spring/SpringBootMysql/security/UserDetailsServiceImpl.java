package com.Spring.SpringBootMysql.security;

import com.Spring.SpringBootMysql.entities.jpa.UserEntity;
import com.Spring.SpringBootMysql.entities.jpa.UserRoleEntity;
import com.Spring.SpringBootMysql.model.User;
import com.Spring.SpringBootMysql.repository.UserRepo;
import com.Spring.SpringBootMysql.repository.UserRoleRepo;
import com.Spring.SpringBootMysql.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepo userRepo;
  private final UserRoleRepo userRoleRepo;

  @Override
  public UserDetails loadUserByUsername(String username) {
//    User user = new User();
    UserEntity user = userRepo.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
    Optional<List<UserRoleEntity>> optionalUserRoleEntities =  userRoleRepo.findByUserId(user.getId());
    List<UserRoleEntity> userRoleEntities = new ArrayList<>();
    if(optionalUserRoleEntities.isPresent()){
      userRoleEntities = optionalUserRoleEntities.get();
    }
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    userRoleEntities.stream().forEach(userRoleEntity -> {
      authorities.add(new SimpleGrantedAuthority(userRoleEntity.getRoleId().toString()));
    });
    return mapUserToCustomUserDetails(user, authorities);
  }


  private CustomUserDetails mapUserToCustomUserDetails(
          UserEntity user, List<SimpleGrantedAuthority> authorities) {
    CustomUserDetails customUserDetails = new CustomUserDetails();
    customUserDetails.setId(user.getId());
    customUserDetails.setUsername(user.getEmail());
    customUserDetails.setPassword(user.getPassword());
    customUserDetails.setName(user.getName());
    customUserDetails.setEmail(user.getEmail());
    customUserDetails.setAuthorities(authorities);
    return customUserDetails;
  }
}
