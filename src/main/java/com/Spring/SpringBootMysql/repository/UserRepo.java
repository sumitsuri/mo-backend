package com.Spring.SpringBootMysql.repository;


import com.Spring.SpringBootMysql.entities.UserEntity;
import com.Spring.SpringBootMysql.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
    UserEntity findByid(Long memberID);

    UserEntity findByemail(String emailId);

    @Query(
            nativeQuery = true,
            value =
                    "select * from user")
    List<UserEntity> findAll();
}
