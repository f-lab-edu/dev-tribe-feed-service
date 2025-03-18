package com.devtribe.devtribe_feed_service.post.domain;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum FeedSortOption {
    BY_NEWEST("최신순", "latest"),
    BY_OLDEST("오래된순", "oldest"),
    BY_UPVOTE("추천순", "upvote"),
    BY_DOWNVOTE("비추천순", "downvote");

    private final String title;
    private final String value;

    FeedSortOption(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public static FeedSortOption fromValue(String value) {
        return Arrays.stream(FeedSortOption.values())
            .filter(feedSortOption -> feedSortOption.valueEquals(value))
            .findAny()
            .orElse(BY_NEWEST);
    }

    private boolean valueEquals(String value) {
        return this.value.equals(value);
    }
}
