package com.devtribe.devtribe_feed_service.post.repository.query;

import com.devtribe.devtribe_feed_service.post.domain.Post;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Component;

@Component
public interface SortQuery {

    BooleanExpression getWhereCondition(Post cursorPost);

    OrderSpecifier<?> getOrderBy();
}
