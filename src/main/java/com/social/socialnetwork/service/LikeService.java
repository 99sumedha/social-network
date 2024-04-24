package com.social.socialnetwork.service;

import com.social.socialnetwork.model.Like;
import com.social.socialnetwork.model.Post;
import com.social.socialnetwork.model.User;
import com.social.socialnetwork.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    private final LikeRepository likeRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public Like likePost(User user, Post post) {
        Like existingLike = findLikeByUserAndPost(user, post);

        if (existingLike != null) {
            throw new RuntimeException("User has already liked this post");
        }

        Like newLike = new Like(user, post);
        return likeRepository.save(newLike);
    }


    public void unlikePost(Like like) {
        likeRepository.delete(like);
    }


    public Like findLikeByUserAndPost(User user, Post post) {
        return likeRepository.findByUserAndPost(user, post);
    }


    public int getLikeCountByPost(Post post) {
        return likeRepository.countByPost(post);
    }
}
