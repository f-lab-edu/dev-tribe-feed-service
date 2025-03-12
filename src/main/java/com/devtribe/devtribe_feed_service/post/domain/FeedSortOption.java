package com.devtribe.devtribe_feed_service.post.domain;

import static com.devtribe.devtribe_feed_service.post.domain.QPost.post;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum FeedSortOption {
    BY_NEWEST("최신순", "latest", post.createdAt.desc(),
        (lastPost) -> post.createdAt.lt(lastPost.getCreatedAt())
            .or(post.createdAt.eq(lastPost.getCreatedAt())
                .and(post.id.lt(lastPost.getId()))
            )),
    BY_OLDEST("오래된순", "oldest", post.createdAt.asc(),
        (lastPost) -> post.createdAt.gt(lastPost.getCreatedAt())
            .or(post.createdAt.eq(lastPost.getCreatedAt())
                .and(post.id.gt(lastPost.getId()))
            )),
    BY_UPVOTE("추천순", "upvote", post.upvoteCount.desc(),
        (lastPost) -> post.upvoteCount.lt(lastPost.getUpvoteCount())
            .or(
                post.upvoteCount.eq(lastPost.getUpvoteCount())
                    .and(post.id.lt(lastPost.getId()))
            )),
    BY_DOWNVOTE("비추천순", "downvote", post.downvoteCount.desc(),
        (lastPost) -> post.downvoteCount.lt(lastPost.getDownvoteCount())
            .or(
                post.downvoteCount.eq(lastPost.getDownvoteCount())
                    .and(post.id.lt(lastPost.getId()))
            ));

    private final String title;
    private final String value;
    private final OrderSpecifier<?> orderSpecifier;
    private final QueryCondition queryCondition;

    FeedSortOption(String title, String value, OrderSpecifier<?> orderSpecifier,
        QueryCondition queryCondition) {
        this.title = title;
        this.value = value;
        this.orderSpecifier = orderSpecifier;
        this.queryCondition = queryCondition;
    }

    public static FeedSortOption fromValue(String value) {
        return Arrays.stream(FeedSortOption.values())
            .filter(feedSortOption -> feedSortOption.valueEquals(value))
            .findAny()
            .orElse(BY_NEWEST);
    }

    public boolean valueEquals(String value) {
        return this.value.equals(value);
    }

    public BooleanExpression getQueryCondition(Post lastPost) {
        return queryCondition.getQuery(lastPost);
    }

    interface QueryCondition {

        BooleanExpression getQuery(Post lastPost);
    }

}
