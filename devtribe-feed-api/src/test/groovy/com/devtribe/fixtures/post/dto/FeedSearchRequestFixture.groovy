package com.devtribe.fixtures.post.dto

import com.devtribe.domain.post.dto.PostFilterCriteria
import com.devtribe.domain.post.dto.PostSortCriteria
import com.devtribe.global.model.FeedSearchRequest

class FeedSearchRequestFixture {

    static FeedSearchRequest createFeedSearchRequest(Map map = [:]) {
        new FeedSearchRequest(
                map.getOrDefault("filter", new PostFilterCriteria()) as PostFilterCriteria,
                map.getOrDefault("sort", PostSortCriteria.NEWEST) as PostSortCriteria,
                map.getOrDefault("offset", 0) as int,
                map.getOrDefault("size", 10) as int,
        )
    }
}