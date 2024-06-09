package com.Spring.SpringBootMysql.repository;

import com.Spring.SpringBootMysql.entities.jpa.UserEntity;
import com.Spring.SpringBootMysql.entities.jpa.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRoleEntity, Long> {

    Optional<List<UserRoleEntity>> findByUserId(long userId);
}
