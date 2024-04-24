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
    User userId;

    @Column(name = "related_user_id", nullable = false)
    private Long friendId;

    @Column(name = "relationship_type", nullable = false)
    private relation relationshipType;

    public Relationship() {
    }

    public Relationship(User userId, Long friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public enum relation {
        FRIEND, FOLLOWER
    }

}
