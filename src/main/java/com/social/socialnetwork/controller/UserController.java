package com.social.socialnetwork.controller;

import com.social.socialnetwork.model.User;
import com.social.socialnetwork.service.RelationshipService;
import com.social.socialnetwork.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final RelationshipService relationshipService;

    public UserController(UserService userService, RelationshipService relationshipService) {
        this.userService = userService;
        this.relationshipService = relationshipService;
    }

    @GetMapping
    public List<User> findAllUsers() {
        return userService.getAllUsers();
    }

    // Endpoint to get all the users by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable (value = "id") long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public User saveUser(@Validated @RequestBody User user) {
        return userService.setUser(user);
    }

    // Endpoint to make friends (userId adds friendId as a friend)
    @PostMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<?> makeFriends(@PathVariable Long userId, @PathVariable Long friendId) {
        try {
            relationshipService.makeFriends(userId, friendId);
            return ResponseEntity.ok("User " + userId + " and User " + friendId + " are now friends");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Endpoint to follow a user (user1Id follows followerId)
    @PostMapping("/{userId}/friends/{followerId}")
    public ResponseEntity<?> followUser(@PathVariable Long userId, @PathVariable Long followerId) {
        try {
            relationshipService.followUser(userId, followerId);
            return ResponseEntity.ok("User " + userId + " is now following User " + followerId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Endpoint to unfollow a user (user1Id unfollows user2Id)
    @DeleteMapping("/{userId}/friends/{followerId}")
    public ResponseEntity<?> unfollowUser(@PathVariable Long userId, @PathVariable Long followerId) {
        try {
            relationshipService.unfollowUser(userId, followerId);
            return ResponseEntity.ok("User " + userId + " unfollowed User " + followerId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
