package com.social.socialnetwork.repository;

import com.social.socialnetwork.model.Post;
import com.social.socialnetwork.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // Custom query method to find all posts by a specific user
    List<Post> findByUser(User user);

    // Custom query method to find all public posts
    List<Post> findByVisibility(Post.Visibility visibility);

}
