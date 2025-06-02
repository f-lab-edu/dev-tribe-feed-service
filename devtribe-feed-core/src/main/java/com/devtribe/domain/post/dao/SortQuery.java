package com.devtribe.domain.post.dao;

import com.querydsl.core.types.OrderSpecifier;

@FunctionalInterface
public interface SortQuery {

    OrderSpecifier<?> getOrderBy();
}
