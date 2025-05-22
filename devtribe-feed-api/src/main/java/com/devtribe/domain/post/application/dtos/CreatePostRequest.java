package com.devtribe.domain.post.application.dtos;


import com.devtribe.domain.post.entity.Post;
import com.devtribe.domain.user.entity.User;

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
