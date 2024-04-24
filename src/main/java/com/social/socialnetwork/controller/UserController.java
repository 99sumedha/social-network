package com.social.socialnetwork.controller;

import com.social.socialnetwork.model.User;
import com.social.socialnetwork.repository.UserRepository;
import com.social.socialnetwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
   private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable (value = "id") long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public User saveUser(@Validated @RequestBody User user) {
        return userService.setUser(user);
    }


}
