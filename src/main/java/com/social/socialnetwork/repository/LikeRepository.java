package com.social.socialnetwork.repository;

import com.social.socialnetwork.model.Like;
import com.social.socialnetwork.model.Post;
import com.social.socialnetwork.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    // Custom query method to find a like by user and post
    Like findByUserAndPost(User user, Post post);

    int countByPost(Post post);

    // Add more custom query methods as needed...
}
