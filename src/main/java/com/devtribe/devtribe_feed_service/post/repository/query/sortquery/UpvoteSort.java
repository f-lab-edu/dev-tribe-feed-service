package com.devtribe.devtribe_feed_service.post.repository.query.sortquery;

import static com.devtribe.devtribe_feed_service.post.domain.QPost.post;

import com.devtribe.devtribe_feed_service.post.domain.Post;
import com.devtribe.devtribe_feed_service.post.repository.query.SortQuery;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;

public class UpvoteSort implements SortQuery {

    @Override
    public BooleanExpression getWhereCondition(Post cursorPost) {
        BooleanExpression lessThenUpvote = post.upvoteCount.lt(cursorPost.getUpvoteCount());
        BooleanExpression ifSameUpvoteThenLessOrEqualId = post.upvoteCount.eq(cursorPost.getUpvoteCount())
            .and(post.id.loe(cursorPost.getId()));

        return lessThenUpvote.or(ifSameUpvoteThenLessOrEqualId);
    }

    @Override
    public OrderSpecifier<?> getOrderBy() {
        return post.upvoteCount.desc();
    }
}
