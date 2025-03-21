package com.devtribe.devtribe_feed_service.post.repository.query;

import static com.devtribe.devtribe_feed_service.post.domain.QPost.post;

import com.devtribe.devtribe_feed_service.global.common.PageResponse;
import com.devtribe.devtribe_feed_service.post.domain.FeedSortOption;
import com.devtribe.devtribe_feed_service.post.domain.Post;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class QueryFeedBySortOption {

    private final SortQueryFactory sortQueryFactory;
    private final JPAQueryFactory queryFactory;

    public QueryFeedBySortOption(SortQueryFactory sortQueryFactory, JPAQueryFactory queryFactory) {
        this.sortQueryFactory = sortQueryFactory;
        this.queryFactory = queryFactory;
    }

    public PageResponse<Post> findAllBySortOption(
        Long cursorId,
        Integer pageSize,
        FeedSortOption feedSortOption
    ) {
        SortQuery sortQuery = sortQueryFactory.getSortQuery(feedSortOption);
        Post cursorPost = getCursorPost(cursorId);
        BooleanExpression whereCondition =
            cursorPost != null ? sortQuery.getWhereCondition(cursorPost) : null;

        List<Post> queryResult = queryFactory
            .selectFrom(post)
            .where(whereCondition)
            .orderBy(sortQuery.getOrderBy())
            .limit(pageSize + 1)
            .fetch();

        return getPageResponse(queryResult, pageSize);
    }

    private Post getCursorPost(Long cursorId) {
        if (cursorId == null) {
            return null;
        }
        return queryFactory.selectFrom(post)
            .where(post.id.eq(cursorId))
            .fetchOne();
    }

    private PageResponse<Post> getPageResponse(List<Post> queryResult, Integer pageSize) {
        if (queryResult.isEmpty()) {
            return new PageResponse<>(List.of(), null, 0L, false);
        }

        boolean hasMore = queryResult.size() > pageSize;
        List<Post> data = queryResult.subList(0, Math.min(queryResult.size(), pageSize));
        Long nextCursor = hasMore ? queryResult.get(pageSize).getId() : null;

        return new PageResponse<>(data, nextCursor, (long) data.size(), hasMore);
    }

}
