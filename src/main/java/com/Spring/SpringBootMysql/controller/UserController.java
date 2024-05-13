package com.Spring.SpringBootMysql.controller;

import com.Spring.SpringBootMysql.Service.UserService;
import com.Spring.SpringBootMysql.domains.request.UserCreationRequest;
import com.Spring.SpringBootMysql.domains.request.UserUpdateRequest;
import com.Spring.SpringBootMysql.domains.response.UserDetails;
import com.Spring.SpringBootMysql.model.User;
import com.Spring.SpringBootMysql.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
@Controller
public class UserController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    UserService userService;
    List<UserDetails> userDetails = new ArrayList<>();
    Set<Integer> userIds = new HashSet<>();

    @GetMapping("/user")
    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    @GetMapping("/employees")
    public List<UserDetails> getAllUsers() {

        /**
         *  {
         *             "id": 1,
         *             "firstName": "Ramesh",
         *             "lastName": "Fadatare",
         *             "email": "ramesh@gmail.com",
         *         },
         *         {
         *             "id": 2,
         *             "firstName": "Umesh",
         *             "lastName": "Fadatare",
         *             "email": "umesh@gmail.com",
         *         },
         *         {
         *             "id": 3,
         *             "firstName": "Rajkumar",
         *             "lastName": "Fadatare",
         *             "email": "rajkumar@gmail.com",
         *         }
         */
        UserDetails userDetails1 = new UserDetails(1,"Ramesh","Fadatare","ramesh@gmail.com");
        UserDetails userDetails2 = new UserDetails(2,"Umesh","Fadatare","umesh@gmail.com");
        UserDetails userDetails3 = new UserDetails(3,"Rajkumar1","Fadatare","rajkumar@gmail.com");
        if(!userIds.contains(userDetails1.getId())){
            userIds.add(userDetails1.getId());
            userDetails.add(userDetails1);
        }
        if(!userIds.contains(userDetails2.getId())){
            userIds.add(userDetails2.getId());
            userDetails.add(userDetails2);
        }
        if(!userIds.contains(userDetails3.getId())){
            userIds.add(userDetails3.getId());
            userDetails.add(userDetails3);
        }
        return userDetails;
    }

    @GetMapping("/employees/{id}")
    public UserDetails getEmployeeById(@PathVariable int id) {
        return userDetails.stream().filter(userDetails1 -> userDetails1.getId() == id).findFirst().get();
    }

    @PutMapping("/employees/{id}")
    public UserDetails getEmployeeById(@PathVariable int id, @RequestBody UserUpdateRequest userUpdateRequest) {
        UserDetails updatedUserDetails = userDetails.stream().filter(userDetails1 -> userDetails1.getId() == id).findFirst().get();
        updatedUserDetails.setFirstName(userUpdateRequest.getFirstName());
        updatedUserDetails.setLastName(userUpdateRequest.getLastName());
        updatedUserDetails.setEmail(userUpdateRequest.getEmail());
        return updatedUserDetails;
    }

    @DeleteMapping("/employees/{id}")
    public UserDetails deleteEmployeeById(@PathVariable int id) {
        UserDetails updatedUserDetails = userDetails.stream().filter(userDetails1 -> userDetails1.getId() == id).findFirst().get();
        userIds.remove(id);
        userDetails.remove(updatedUserDetails);
        return updatedUserDetails;
    }

    @PostMapping("/employees")
    public UserDetails createEmployee(@RequestBody UserCreationRequest userCreationRequest) {
        int id = getId();
        UserDetails userDetails1 = new UserDetails(id,userCreationRequest.getFirstName(),userCreationRequest.getLastName(),userCreationRequest.getEmail());
        if(!userIds.contains(userDetails1.getId())){
            userIds.add(userDetails1.getId());
            userDetails.add(userDetails1);
        }
        return userDetails1;
    }
    private int getId(){
        List<Integer> ids = new ArrayList<>(userIds);
       Collections.sort(ids);
       return ids.get(ids.size()-1)+1;
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
