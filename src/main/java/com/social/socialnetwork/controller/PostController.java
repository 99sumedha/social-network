package com.social.socialnetwork.controller;

import com.social.socialnetwork.DTO.PostResponseDTO;
import com.social.socialnetwork.model.Post;
import com.social.socialnetwork.DTO.PostRequestDTO;
import com.social.socialnetwork.model.User;
import com.social.socialnetwork.service.LikeService;
import com.social.socialnetwork.service.PostService;
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

    @Autowired
    public PostController(PostService postService, LikeService likeService) {
        this.postService = postService;
        this.likeService = likeService;
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

    // Endpoint to like a post
    @PostMapping("/{postId}/like")
    public ResponseEntity<String> likePost(@PathVariable User user, Post post) {
        likeService.likePost(user, post);
        return ResponseEntity.ok("Post liked successfully");
    }

    // Endpoint to unlike a post
    @PostMapping("/{postId}/unlike")
    public ResponseEntity<String> unlikePost(@PathVariable Long postId) {
        postService.unlikePost(postId);
        return ResponseEntity.ok("Post unliked successfully");
    }
}
