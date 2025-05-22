package com.devtribe.fixtures.post.dto

import com.devtribe.domain.post.application.dtos.PostResponse
import com.devtribe.domain.post.entity.Publication

import java.time.LocalDateTime

class PostResponseFixture {

    static List<PostResponse> createLatestPostResponse(int numberOfPost) {
        LocalDateTime now = LocalDateTime.now()

        (0..numberOfPost-1).collect { i ->
            new PostResponse(
                    (i + 1) as Long,
                    (i + 1) as Long,
                    "Title ${i + 1}",
                    "Content of post ${i + 1}",
                    "Thumbnail ${i + 1}",
                    Publication.PUBLIC,
                    now.minusMinutes(i),
                    now.minusMinutes(i)
            )
        }
    }
}
