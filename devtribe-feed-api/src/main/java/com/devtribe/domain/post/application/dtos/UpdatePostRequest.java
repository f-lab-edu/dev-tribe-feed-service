package com.devtribe.domain.post.application.dtos;

import com.devtribe.domain.post.entity.Publication;

public record UpdatePostRequest(
    Long authorId,
    String title,
    String content,
    String thumbnail,
    Publication publication
) {

}
