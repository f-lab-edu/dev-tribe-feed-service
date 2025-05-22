package com.devtribe.fixtures.post.dto

import com.devtribe.domain.post.entity.FeedFilterOption

import java.time.LocalDateTime

class FeedFilterOptionFixture {
    static FeedFilterOption createFeedFilterOption(Map map = [:]) {
        new FeedFilterOption(
                keyword: map.getOrDefault("keyword", null) as String,
                startDate: map.getOrDefault("startDate", null) as LocalDateTime,
                endDate: map.getOrDefault("endDate", null) as LocalDateTime,
                authorId: map.getOrDefault("authorId", null) as Long,
        )
    }
}
