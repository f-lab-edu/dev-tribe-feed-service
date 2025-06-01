package com.devtribe.global.model;

import com.devtribe.domain.post.entity.FeedFilterOption;
import com.devtribe.domain.post.entity.FeedSortOption;
import com.fasterxml.jackson.annotation.JsonProperty;

public record FeedSearchRequest(
    @JsonProperty("filter") FeedFilterOption feedFilterOption,
    @JsonProperty("sort") FeedSortOption feedSortOption,
    int offset,
    int size
) {
    private static final int DEFAULT_PAGE_SIZE = 10;

    public FeedSearchRequest() {
        this(new FeedFilterOption(), FeedSortOption.NEWEST, 0, DEFAULT_PAGE_SIZE);
    }

    public FeedSearchRequest(
        FeedFilterOption feedFilterOption,
        FeedSortOption feedSortOption,
        int offset,
        int size
    ) {
        this.feedFilterOption = (feedFilterOption != null) ? feedFilterOption : new FeedFilterOption();
        this.feedSortOption = (feedSortOption != null) ? feedSortOption : FeedSortOption.NEWEST;
        this.offset = offset;
        this.size = (size > 0) ? size : DEFAULT_PAGE_SIZE;
    }
}