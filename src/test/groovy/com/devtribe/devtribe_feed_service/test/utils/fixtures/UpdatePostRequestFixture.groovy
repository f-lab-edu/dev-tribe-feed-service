package com.devtribe.devtribe_feed_service.test.utils.fixtures

import com.devtribe.devtribe_feed_service.post.application.dtos.UpdatePostRequest
import com.devtribe.devtribe_feed_service.post.domain.Publication

class UpdatePostRequestFixture {

    static UpdatePostRequest getUpdatePostRequest(Long userId, Publication publication) {
        return new UpdatePostRequest(
                userId,
                "title updated",
                "content updated",
                "thumbnail updated",
                publication
        )
    }
}
