package com.devtribe.devtribe_feed_service.post.repository.query;

import com.querydsl.core.types.OrderSpecifier;

@FunctionalInterface
public interface SortQuery {

    OrderSpecifier<?> getOrderBy();
}
