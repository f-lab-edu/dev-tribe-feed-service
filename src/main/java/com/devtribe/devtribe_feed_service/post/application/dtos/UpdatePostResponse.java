package com.devtribe.devtribe_feed_service.post.application.dtos;

import com.devtribe.devtribe_feed_service.post.domain.Post;
import com.devtribe.devtribe_feed_service.post.domain.Publication;

public record UpdatePostResponse(String title, String content, String thumbnail,
                                 Publication publication) {

    public static UpdatePostResponse from(Post post) {
        return new UpdatePostResponse(
            post.getTitle(),
            post.getContent(),
            post.getThumbnail(),
            post.getPublication()
        );
    }
}
