package com.Spring.SpringBootMysql.repository;


import com.Spring.SpringBootMysql.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByid(Long memberID);

    User findByemail(String emailId);

    @Query(
            nativeQuery = true,
            value =
                    "select * from user")
    List<User> findAll();
}
