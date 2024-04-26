package com.social.socialnetwork.model;

import jakarta.persistence.*;


@Entity
@Table(name = "relationship")
public class Relationship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "related_user_id", nullable = false)
    private User friendId;

    @Column(name = "relationship_type", nullable = false)
    private relation relationshipType;


    public Relationship() {

    }

    public Relationship(User user1, User user2, relation type) {
        this.userId = user1;
        this.friendId = user2;
        this.relationshipType = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public User getFriendId() {
        return friendId;
    }

    public void setFriendId(User friendId) {
        this.friendId = friendId;
    }

    public relation getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(relation relationshipType) {
        this.relationshipType = relationshipType;
    }

    public enum relation {
        FRIEND, FOLLOWER
    }

}
