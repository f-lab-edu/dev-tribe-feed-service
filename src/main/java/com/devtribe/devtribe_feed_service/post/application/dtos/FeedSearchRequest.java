package com.devtribe.devtribe_feed_service.post.application.dtos;

import com.devtribe.devtribe_feed_service.post.domain.FeedFilterOption;
import com.devtribe.devtribe_feed_service.post.domain.FeedSortOption;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class FeedSearchRequest {

    private static final int DEFAULT_PAGE_SIZE = 10;

    @JsonProperty("filter")
    private FeedFilterOption feedFilterOption = new FeedFilterOption();

    @JsonProperty("sort")
    private FeedSortOption feedSortOption = FeedSortOption.NEWEST;

    private int offset = 0;

    private int size = DEFAULT_PAGE_SIZE;
}
