package com.social.socialnetwork.service;

import com.social.socialnetwork.model.Post;
import com.social.socialnetwork.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post createPost(PostRequestDTO postRequest) {
        // Implement post creation logic here
    }

    @Override
    public List<PostResponseDTO> getAllPosts() {
        // Implement post retrieval logic here
    }

    @Override
    public void likePost(Long postId) {
        // Implement post liking logic here
    }

    @Override
    public void unlikePost(Long postId) {
        // Implement post unliking logic here
    }
}
