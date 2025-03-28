package com.devtribe.devtribe_feed_service.test.utils.fixtures.post

import com.devtribe.devtribe_feed_service.post.application.dtos.CreatePostRequest

class CreatePostRequestFixture {

    static CreatePostRequest getCreatePostRequest(Long authorId) {
        return new CreatePostRequest(
                "title",
                "content",
                authorId,
                "thumbnail"
        )
    }
}
