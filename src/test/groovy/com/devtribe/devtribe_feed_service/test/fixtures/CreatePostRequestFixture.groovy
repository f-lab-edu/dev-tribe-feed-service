package com.devtribe.devtribe_feed_service.test.fixtures

import com.devtribe.devtribe_feed_service.post.application.dtos.CreatePostRequest
import com.devtribe.devtribe_feed_service.post.domain.ContentSource

class CreatePostRequestFixture {

    static CreatePostRequest getCreatePostRequest(Long authorId, ContentSource contentSource) {
        return new CreatePostRequest(
                "title",
                "content",
                authorId,
                contentSource,
                "thumbnail"
        )
    }
}
