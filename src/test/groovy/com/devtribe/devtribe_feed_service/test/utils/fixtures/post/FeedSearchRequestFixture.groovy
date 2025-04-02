package com.devtribe.devtribe_feed_service.test.utils.fixtures.post

import com.devtribe.devtribe_feed_service.post.application.dtos.FeedSearchRequest
import com.devtribe.devtribe_feed_service.post.domain.FeedFilterOption
import com.devtribe.devtribe_feed_service.post.domain.FeedSortOption

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