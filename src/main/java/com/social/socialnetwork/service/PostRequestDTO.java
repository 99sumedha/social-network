package com.social.socialnetwork.service;

import com.social.socialnetwork.model.Visibility;

public class PostRequestDTO {
    private Long userId;
    private String text;
    private Visibility visibility;

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

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }
}
