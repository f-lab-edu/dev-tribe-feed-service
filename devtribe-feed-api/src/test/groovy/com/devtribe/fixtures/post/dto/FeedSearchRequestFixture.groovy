package com.devtribe.fixtures.post.dto

import com.devtribe.domain.post.entity.FeedFilterOption
import com.devtribe.domain.post.entity.FeedSortOption
import com.devtribe.global.model.FeedSearchRequest


class FeedSearchRequestFixture {

    static FeedSearchRequest createFeedSearchRequest(Map map = [:]) {
        new FeedSearchRequest(
                map.getOrDefault("filter", new FeedFilterOption()) as FeedFilterOption,
                map.getOrDefault("sort", FeedSortOption.NEWEST) as FeedSortOption,
                map.getOrDefault("offset", 0) as int,
                map.getOrDefault("size", 10) as int,
        )
    }
}