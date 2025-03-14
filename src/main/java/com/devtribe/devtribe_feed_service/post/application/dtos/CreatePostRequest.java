package com.devtribe.devtribe_feed_service.post.application.dtos;

import com.devtribe.devtribe_feed_service.post.domain.Post;
import com.devtribe.devtribe_feed_service.user.domain.User;

public record CreatePostRequest(String title, String content, Long authorId, String thumbnail) {

    public Post toEntity(User user) {
        return Post.builder()
            .title(title)
            .content(content)
            .userId(user.getId())
            .thumbnail(thumbnail)
            .build();
    }
}
