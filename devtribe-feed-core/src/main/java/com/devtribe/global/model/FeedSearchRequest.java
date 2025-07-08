package com.devtribe.global.model;

import com.devtribe.domain.post.dto.PostFilterCriteria;
import com.devtribe.domain.post.dto.PostSortCriteria;
import com.fasterxml.jackson.annotation.JsonProperty;

public record FeedSearchRequest(
    @JsonProperty("filter") PostFilterCriteria postFilterCriteria,
    @JsonProperty("sort") PostSortCriteria postSortCriteria,
    int offset,
    int size
) {
    private static final int DEFAULT_PAGE_SIZE = 10;

    public FeedSearchRequest() {
        this(new PostFilterCriteria(), PostSortCriteria.NEWEST, 0, DEFAULT_PAGE_SIZE);
    }

    public FeedSearchRequest(
        PostFilterCriteria postFilterCriteria,
        PostSortCriteria postSortCriteria,
        int offset,
        int size
    ) {
        this.postFilterCriteria = (postFilterCriteria != null) ? postFilterCriteria
            : new PostFilterCriteria();
        this.postSortCriteria = (postSortCriteria != null) ? postSortCriteria : PostSortCriteria.NEWEST;
        this.offset = offset;
        this.size = (size > 0) ? size : DEFAULT_PAGE_SIZE;
    }
}