package com.social.socialnetwork.service;

import com.social.socialnetwork.DTO.PostRequestDTO;
import com.social.socialnetwork.DTO.PostResponseDTO;
import com.social.socialnetwork.model.Post;
import com.social.socialnetwork.model.Relationship;
import com.social.socialnetwork.model.User;
import com.social.socialnetwork.repository.LikeRepository;
import com.social.socialnetwork.repository.PostRepository;
import com.social.socialnetwork.repository.RelationshipRepository;
import com.social.socialnetwork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    private final RelationshipRepository relationshipRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, LikeRepository likeRepository, RelationshipRepository relationshipRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.relationshipRepository = relationshipRepository;
    }

    @Transactional
    public Post createPost(PostRequestDTO postRequest) {
        // Get user by userId from database
        User user = userRepository.findById(postRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Create a new post entity
        Post post = new Post(user, postRequest.getText(), postRequest.getVisibility());

        // Save the post in the database
        return postRepository.save(post);
    }


    public List<PostResponseDTO> getAllPosts() {
        // Get all posts from the database
        List<Post> posts = postRepository.findAll();

        // Map Post entities to PostResponseDTOs
        return mapPostsToDTOs(posts);
    }

    public List<PostResponseDTO> getWallPosts(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<User> friends = relationshipRepository.findByUserIdAndRelationshipType(user, Relationship.relation.FRIEND);
        List<User> following = relationshipRepository.findByUserIdAndRelationshipType(user, Relationship.relation.FOLLOWER);

        List<Post> wallPosts = new ArrayList<>();

        // Add user's own posts
        wallPosts.addAll(postRepository.getAllByUser(user));

        // Add posts from friends (both public and private)
        for (User friend : friends) {
            wallPosts.addAll(postRepository.findByUserAndVisibilityIn(friend, List.of(Post.Visibility.PUBLIC, Post.Visibility.PRIVATE)));
        }

        // Add public posts from following users
        for (User followedUser : following) {
            wallPosts.addAll(postRepository.findByUserAndVisibility(followedUser, Post.Visibility.PUBLIC));
        }

        for (Post post : wallPosts) {
            int likesCount = likeRepository.countByPost(post);
            System.out.println(likesCount);
        }


        return mapPostsToDTOs(wallPosts);
    }

    public ResponseEntity<Post> getPostById (long id) {

        Optional<Post> post = postRepository.findById(id);

        if (post.isPresent()){
            return ResponseEntity.ok().body(post.get());
        } else {
            return ResponseEntity.notFound().build();
        }
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
        dto.setLikesCount(post.getLikes().size());

        // Set author information
        dto.setAuthorName(post.getUser().getFullName());


        return dto;
    }
}
