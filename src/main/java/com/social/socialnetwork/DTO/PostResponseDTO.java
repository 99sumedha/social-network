package com.social.socialnetwork.DTO;

import com.social.socialnetwork.model.Post;

import java.time.LocalDateTime;

public class PostResponseDTO {
    private Long postId;
    private String text;
    private String authorName;
    private Post.Visibility visibility;
    private LocalDateTime createdAt;
    private int likesCount;


    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Post.Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Post.Visibility visibility) {
        this.visibility = visibility;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String fullName) {
        this.authorName = fullName;
    }
}
