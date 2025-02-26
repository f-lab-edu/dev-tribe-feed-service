package com.devtribe.devtribe_feed_service.post.application.dtos;

import com.devtribe.devtribe_feed_service.post.domain.Publication;

public record UpdatePostRequest(Long authorId, String title, String content, String thumbnail,
                                Publication publication) {

}
