package com.Spring.SpringBootMysql.Service.ServiceImpl;

import com.Spring.SpringBootMysql.Service.UserOpsService;
import com.Spring.SpringBootMysql.entities.UserEntity;
import com.Spring.SpringBootMysql.model.User;
import com.Spring.SpringBootMysql.repository.UserRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserOpsServiceImpl implements UserOpsService {
    private static final Logger LOG = LoggerFactory.getLogger(UserOpsService.class);
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserEntity findBymemberID(Long memberID) {
        return userRepo.findByid(memberID);
    }

    @Override
    public UserEntity findByemailId(String emailId) {
        return userRepo.findByemail(emailId);
    }

    @Override
    public UserEntity save(UserEntity user) {
        return userRepo.save(user);
    }

    @Override
    public List<UserEntity> findAll() {

        return userRepo.findAll();
    }

    @Override
    public UserEntity addMember(UserEntity user) {
        user.setId(user.getId());
//        user.setCreatedAt(new Date());
//        user.setUpdatedAt(new Date());
        return userRepo.save(user);
    }
}
