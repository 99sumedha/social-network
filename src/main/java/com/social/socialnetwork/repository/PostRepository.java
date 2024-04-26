package com.social.socialnetwork.repository;

import com.social.socialnetwork.model.Post;
import com.social.socialnetwork.model.Relationship;
import com.social.socialnetwork.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // Custom query method to find all posts by a specific user
    List<Post> getAllByUser(User user);

    // Custom query method to find a list of posts by the users with visibility
    List<Post> findByUserAndVisibilityIn(User friend, List<Object> objects);

    // Custom query method to find a list of posts by the users with visibility
    List<Post> findByUserAndVisibility(User friend, Post.Visibility type);
}
