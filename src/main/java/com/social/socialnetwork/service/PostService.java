package com.social.socialnetwork.service;

import com.social.socialnetwork.model.Post;

import java.util.List;

public interface PostService {
    Post createPost(PostRequestDTO postRequest);
    List<PostResponseDTO> getAllPosts();
    void likePost(Long postId);
    void unlikePost(Long postId);
}