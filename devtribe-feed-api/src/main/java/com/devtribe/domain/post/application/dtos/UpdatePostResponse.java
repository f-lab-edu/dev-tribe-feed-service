package com.devtribe.domain.post.application.dtos;


import com.devtribe.domain.post.entity.Post;
import com.devtribe.domain.post.entity.Publication;

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
