package com.devtribe.fixtures.post.dto

import com.devtribe.domain.post.application.dtos.UpdatePostRequest
import com.devtribe.domain.post.entity.Publication

class UpdatePostRequestFixture {

    static UpdatePostRequest getUpdatePostRequest(Publication publication) {
        return new UpdatePostRequest(
                "title updated",
                "content updated",
                "thumbnail updated",
                publication,
                List.of(1L)
        )
    }
}
