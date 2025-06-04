package com.devtribe.fixtures.post.dto

import com.devtribe.domain.post.application.dtos.CreatePostRequest

class CreatePostRequestFixture {
    static CreatePostRequest createPostRequest(Map<String, Object> overrides = [:]) {
        String title = overrides.get("title") ?: "title"
        String content = overrides.get("content") ?: "content"
        String thumbnail = overrides.get("thumbnail") ?: "thumbnail"
        List<Long> tags = (overrides.get("tags") ?: [1L, 2L]) as List<Long>

        new CreatePostRequest(title, content, thumbnail, tags)
    }
}
