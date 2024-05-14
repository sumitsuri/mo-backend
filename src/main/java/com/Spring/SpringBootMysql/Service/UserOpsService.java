package com.Spring.SpringBootMysql.Service;


import com.Spring.SpringBootMysql.entities.UserEntity;
import com.Spring.SpringBootMysql.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service()
public interface UserOpsService {

    UserEntity findBymemberID(Long memberID);

    UserEntity findByemailId(String emailId);

    UserEntity save(UserEntity user);

    UserEntity addMember(UserEntity user);

    List<UserEntity> findAll();
}
