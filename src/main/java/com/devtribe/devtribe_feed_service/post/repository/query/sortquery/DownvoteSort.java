package com.devtribe.devtribe_feed_service.post.repository.query.sortquery;

import static com.devtribe.devtribe_feed_service.post.domain.QPost.post;

import com.devtribe.devtribe_feed_service.post.domain.Post;
import com.devtribe.devtribe_feed_service.post.repository.query.SortQuery;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;

public class DownvoteSort implements SortQuery {

    @Override
    public BooleanExpression getWhereCondition(Post cursorPost) {
        BooleanExpression lessThanDownvote = post.downvoteCount.lt(cursorPost.getDownvoteCount());
        BooleanExpression ifSameDownvoteThanLessOrEqualId = post.downvoteCount.eq(cursorPost.getDownvoteCount())
            .and(post.id.loe(cursorPost.getId()));

        return lessThanDownvote.or(ifSameDownvoteThanLessOrEqualId);
    }

    @Override
    public OrderSpecifier<?> getOrderBy() {
        return post.downvoteCount.desc();
    }
}
