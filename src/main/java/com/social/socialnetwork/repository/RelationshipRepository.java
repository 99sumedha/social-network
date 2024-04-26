package com.social.socialnetwork.repository;

import com.social.socialnetwork.model.Relationship;
import com.social.socialnetwork.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, Long> {

    // Custom query to find a relationship by user and related user
    Relationship findByUserIdAndFriendId(User user, User relatedUser);

    // Custom query to count relationships of a specific type for a user
    int countByUserIdAndRelationshipType(User user, Relationship.relation type);

    // Custom query to find user and relationship type friend
    List<User> findByUserIdAndRelationshipType(User userId, Relationship.relation type);

    // Custom query to find friend and relationship type with user
    List<User> findByFriendIdAndRelationshipType(User user, Relationship.relation relation);

}
