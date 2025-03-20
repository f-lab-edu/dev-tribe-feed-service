package com.devtribe.devtribe_feed_service.post.repository.query.sortquery;

import static com.devtribe.devtribe_feed_service.post.domain.QPost.post;

import com.devtribe.devtribe_feed_service.post.domain.Post;
import com.devtribe.devtribe_feed_service.post.repository.query.SortQuery;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;

public class OldestSort implements SortQuery {

    @Override
    public BooleanExpression getWhereCondition(Post cursorPost) {
        BooleanExpression greaterThanCreatedAt = post.createdAt.gt(cursorPost.getCreatedAt());
        BooleanExpression ifSameCreatedThanGreaterOrEqualId = post.createdAt.eq(cursorPost.getCreatedAt())
            .and(post.id.goe(cursorPost.getId()));

        return greaterThanCreatedAt.or(ifSameCreatedThanGreaterOrEqualId);
    }

    @Override
    public OrderSpecifier<?> getOrderBy() {
        return post.createdAt.asc();
    }
}
