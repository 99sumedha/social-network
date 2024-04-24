package com.social.socialnetwork.service;

import com.social.socialnetwork.DTO.PostRequestDTO;
import com.social.socialnetwork.DTO.PostResponseDTO;
import com.social.socialnetwork.model.Post;
import com.social.socialnetwork.model.User;
import com.social.socialnetwork.repository.LikeRepository;
import com.social.socialnetwork.repository.PostRepository;
import com.social.socialnetwork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository; // Assuming LikeRepository is used for managing likes

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, LikeRepository likeRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
    }

    @Transactional
    public Post createPost(PostRequestDTO postRequest) {
        // Retrieve user by userId from database (assuming userId is provided in postRequest)
        User user = userRepository.findById(postRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Create a new post entity
        Post post = new Post(user, postRequest.getText(), postRequest.getVisibility());

        // Save the post in the database
        return postRepository.save(post);
    }

    public List<PostResponseDTO> getAllPosts() {
        // Retrieve all posts from the database
        List<Post> posts = postRepository.findAll();

        // Map Post entities to PostResponseDTOs
        return mapPostsToDTOs(posts);
    }

    public ResponseEntity<Post> getPostById (long id) {

        Optional<Post> post = postRepository.findById(id);

        if (post.isPresent()){
            return ResponseEntity.ok().body(post.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public void likePost(Long userId, Long postId) {

    }


    public void unlikePost(Long postId) {
        // Implementation of unlikePost method...
    }

    // Helper method to map Post entities to PostResponseDTOs
    private List<PostResponseDTO> mapPostsToDTOs(List<Post> posts) {
        return posts.stream()
                .map(this::mapPostToDTO)
                .collect(Collectors.toList());
    }

    // Helper method to map a single Post entity to PostResponseDTO
    private PostResponseDTO mapPostToDTO(Post post) {
        PostResponseDTO dto = new PostResponseDTO();
        dto.setPostId(post.getPostId());
        dto.setText(post.getText());
        dto.setVisibility(post.getVisibility());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setLikesCount(post.getLikes().size()); // Assuming Post entity has a collection of likes

        // Set author information (assuming User entity is associated with Post entity)
        dto.setAuthorName(post.getUser().getFullName());
        // You can map other author-related information here if needed

        return dto;
    }
}
