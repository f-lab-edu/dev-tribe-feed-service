package com.devtribe.domain.post.application.dtos;

import com.devtribe.domain.post.entity.Post;
import com.devtribe.domain.post.entity.Publication;
import java.time.LocalDateTime;

public record PostResponse(Long id, Long authorId, String title, String content, String thumbnail,
                           Publication publication, LocalDateTime createdAt,
                           LocalDateTime updatedAt) {

    public static PostResponse from(Post post) {
        return new PostResponse(
            post.getId(),
            post.getUserId(),
            post.getTitle(),
            post.getContent(),
            post.getThumbnail(),
            post.getPublication(),
            post.getCreatedAt(),
            post.getUpdatedAt()
        );
    }
}
