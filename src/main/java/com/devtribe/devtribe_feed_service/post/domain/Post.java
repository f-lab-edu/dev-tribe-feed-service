package com.devtribe.devtribe_feed_service.post.domain;

import com.devtribe.devtribe_feed_service.user.domain.User;
import lombok.Builder;

public class Post {

    private Long id;
    private String title;
    private String content;
    private User author;
    private String thumbnail;

    @Builder
    public Post(String title, String body, User author, String thumbnail) {
        this.title = title;
        this.content = body;
        this.author = author;
        this.thumbnail = thumbnail;
    }
}
