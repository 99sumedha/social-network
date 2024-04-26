package com.social.socialnetwork.controller;

import com.social.socialnetwork.model.Like;
import com.social.socialnetwork.model.Post;
import com.social.socialnetwork.model.User;
import com.social.socialnetwork.service.LikeService;
import com.social.socialnetwork.service.PostService;
import com.social.socialnetwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public LikeController(LikeService likeService, UserService userService, PostService postService) {
        this.likeService = likeService;
        this.userService = userService;
        this.postService = postService;
    }

    // Endpoint to like a post
    @PostMapping("/like")
    public ResponseEntity<?> likePost(@RequestBody Map<String, Long> payload) {
        User user = userService.getUserById(payload.get("userId")).getBody();
        Post post = postService.getPostById(payload.get("postId")).getBody();

        if (user == null || post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or post not found");
        }

        Like existingLike = likeService.findLikeByUserAndPost(user, post);

        if (existingLike != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User has already liked this post");
        }

        Like like = likeService.likePost(user, post);
        return ResponseEntity.ok("Post liked successfully!");
    }

    // Endpoint to unlike a post
    @DeleteMapping("/unlike")
    public ResponseEntity<?> unlikePost(@RequestBody Map<String, Long> payload) {
        User user = userService.getUserById(payload.get("userId")).getBody();
        Post post = postService.getPostById(payload.get("postId")).getBody();

        if (user == null || post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or post not found");
        }

        Like existingLike = likeService.findLikeByUserAndPost(user, post);

        if (existingLike == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User has not liked this post");
        }

        likeService.unlikePost(existingLike);
        return ResponseEntity.ok("Post unliked successfully");
    }
}
