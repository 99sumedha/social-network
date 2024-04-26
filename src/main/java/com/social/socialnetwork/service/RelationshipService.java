package com.social.socialnetwork.service;

import com.social.socialnetwork.model.Relationship;
import com.social.socialnetwork.model.User;
import com.social.socialnetwork.repository.RelationshipRepository;
import com.social.socialnetwork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RelationshipService {

    private final UserRepository userRepository;
    private final RelationshipRepository relationshipRepository;

    @Autowired
    public RelationshipService(UserRepository userRepository, RelationshipRepository relationshipRepository) {
        this.userRepository = userRepository;
        this.relationshipRepository = relationshipRepository;
    }

    public void makeFriends(Long user1Id, Long user2Id) {
        User user1 = userRepository.findById(user1Id)
                .orElseThrow(() -> new RuntimeException("User with id " + user1Id + " not found"));
        User user2 = userRepository.findById(user2Id)
                .orElseThrow(() -> new RuntimeException("User with id " + user2Id + " not found"));

        if (user1.equals(user2)) {
            throw new RuntimeException("Cannot add yourself as a friend");
        }

        relationshipRepository.save(new Relationship(user1, user2, Relationship.relation.FRIEND));
        relationshipRepository.save(new Relationship(user2, user1, Relationship.relation.FRIEND));
    }

    public void followUser(Long followerId, Long followeeId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("Follower with id " + followerId + " not found"));
        User followee = userRepository.findById(followeeId)
                .orElseThrow(() -> new RuntimeException("Followee with id " + followeeId + " not found"));

        if (follower.equals(followee)) {
            throw new RuntimeException("Cannot follow yourself");
        }

        relationshipRepository.save(new Relationship(follower, followee, Relationship.relation.FOLLOWER));
    }

    public void unfollowUser(Long followerId, Long followeeId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("Follower with id " + followerId + " not found"));
        User followee = userRepository.findById(followeeId)
                .orElseThrow(() -> new RuntimeException("Followee with id " + followeeId + " not found"));

        Relationship relationship = relationshipRepository.findByUserIdAndFriendId(follower, followee);

        if (relationship != null && relationship.getRelationshipType() == Relationship.relation.FOLLOWER) {
            relationshipRepository.delete(relationship);
        } else {
            throw new RuntimeException("Cannot unfollow a user you are not following");
        }
    }

    public List<User> getFriends(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with id " + userId + " not found"));

        return relationshipRepository.findByUserIdAndRelationshipType(user, Relationship.relation.FRIEND);
    }

    public List<User> getFollowers(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with id " + userId + " not found"));

        return relationshipRepository.findByFriendIdAndRelationshipType(user, Relationship.relation.FOLLOWER);
    }

    public List<User> getFollowing(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with id " + userId + " not found"));

        return relationshipRepository.findByUserIdAndRelationshipType(user, Relationship.relation.FOLLOWER);
    }

    public int countFriends(Long userId) {
        return relationshipRepository.countByUserIdAndRelationshipType(userRepository.getById(userId), Relationship.relation.FRIEND);
    }

    public int countFollowers(Long userId) {
        return relationshipRepository.countByUserIdAndRelationshipType(userRepository.getById(userId), Relationship.relation.FOLLOWER);
    }

    public int countFollowing(Long userId) {
        return relationshipRepository.countByUserIdAndRelationshipType(userRepository.getById(userId), Relationship.relation.FOLLOWER);
    }
}
