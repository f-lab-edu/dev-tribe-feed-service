package com.devtribe.devtribe_feed_service.post.repository.query.sortquery;

import static com.devtribe.devtribe_feed_service.post.domain.QPost.post;

import com.devtribe.devtribe_feed_service.post.domain.Post;
import com.devtribe.devtribe_feed_service.post.repository.query.SortQuery;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;

public class NewestSort implements SortQuery {

    @Override
    public BooleanExpression getWhereCondition(Post cursorPost) {
        BooleanExpression lessThanCreated = post.createdAt.lt(cursorPost.getCreatedAt());
        BooleanExpression ifSameCreatedThanLessOrEqualId = post.createdAt.eq(cursorPost.getCreatedAt())
            .and(post.id.loe(cursorPost.getId()));

        return lessThanCreated.or(ifSameCreatedThanLessOrEqualId);
    }

    @Override
    public OrderSpecifier<?> getOrderBy() {
        return post.createdAt.desc();
    }
}
