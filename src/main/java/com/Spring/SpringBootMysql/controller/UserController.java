package com.Spring.SpringBootMysql.controller;

import com.Spring.SpringBootMysql.Service.UserService;
import com.Spring.SpringBootMysql.model.User;
import com.Spring.SpringBootMysql.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/user-svc")
@Controller
public class UserController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    UserService userService;

    @GetMapping("/user")
    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    @PostMapping("/user")
    public User createMember(@RequestBody User user) {
//        user.setCreatedAt(new Date());
//        user.setUpdatedAt(new Date());
        return userService.save(user);
    }

    @PostMapping("/addMember")
    public User addMember(@RequestBody User user) {
        return userService.addMember(user);
    }
}
