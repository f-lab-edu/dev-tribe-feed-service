package com.devtribe.fixtures.post.dto

import com.devtribe.domain.post.dto.PostFilterCriteria

import java.time.LocalDateTime

class FeedFilterOptionFixture {
    static PostFilterCriteria createFeedFilterOption(Map map = [:]) {
        new PostFilterCriteria(
                startDate: map.getOrDefault("startDate", null) as LocalDateTime,
                endDate: map.getOrDefault("endDate", null) as LocalDateTime,
                authorId: map.getOrDefault("authorId", null) as Long,
        )
    }
}
