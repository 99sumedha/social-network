package com.social.socialnetwork.DTO;

import com.social.socialnetwork.model.Post;

public class PostRequestDTO {
    private Long userId;
    private String text;
    private Post.Visibility visibility;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
}
