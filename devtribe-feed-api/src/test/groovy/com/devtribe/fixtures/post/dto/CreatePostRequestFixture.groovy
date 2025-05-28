package com.devtribe.fixtures.post.dto

import com.devtribe.domain.post.application.dtos.CreatePostRequest


class CreatePostRequestFixture {

    static CreatePostRequest getCreatePostRequest(Long authorId) {
        return new CreatePostRequest(
                "title",
                "content",
                authorId,
                "thumbnail",
                List.of(1L, 2L)
        )
    }
}
