package com.social.socialnetwork.controller;

import com.social.socialnetwork.DTO.PostResponseDTO;
import com.social.socialnetwork.model.Like;
import com.social.socialnetwork.model.Post;
import com.social.socialnetwork.DTO.PostRequestDTO;
import com.social.socialnetwork.model.User;
import com.social.socialnetwork.service.LikeService;
import com.social.socialnetwork.service.PostService;
import com.social.socialnetwork.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final LikeService likeService;

    private final RelationshipService relationshipService;

    @Autowired
    public PostController(PostService postService, LikeService likeService, RelationshipService relationshipService) {
        this.postService = postService;
        this.likeService = likeService;
        this.relationshipService = relationshipService;
    }

    // Endpoint to create a new post
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostRequestDTO postRequest) {
        Post createdPost = postService.createPost(postRequest);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    // Endpoint to fetch all posts (public and user-specific)
    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getAllPosts() {
        List<PostResponseDTO> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/walls/{userId}")
    public ResponseEntity<List<PostResponseDTO>> getSortedPosts(@PathVariable Long userId) {
        try {
            List<PostResponseDTO> wallPosts = postService.getWallPosts(userId);
            return ResponseEntity.ok(wallPosts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Endpoint to like a post
    @PostMapping("/{postId}/like")
    public ResponseEntity<String> likePost(@PathVariable User user, Post post) {
        likeService.likePost(user, post);
        return ResponseEntity.ok("Post liked successfully");
    }

    // Endpoint to unlike a post
    @PostMapping("/{postId}/unlike")
    public ResponseEntity<String> unlikePost(@PathVariable Like like) {
        likeService.unlikePost(like);
        return ResponseEntity.ok("Post unliked successfully");
    }

}
