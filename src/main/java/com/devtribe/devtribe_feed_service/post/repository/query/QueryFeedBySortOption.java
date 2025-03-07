package com.devtribe.devtribe_feed_service.post.repository.query;

import static com.devtribe.devtribe_feed_service.post.domain.QPost.post;

import com.devtribe.devtribe_feed_service.global.common.CursorPagination;
import com.devtribe.devtribe_feed_service.global.common.PageResponse;
import com.devtribe.devtribe_feed_service.post.domain.FeedSortOption;
import com.devtribe.devtribe_feed_service.post.domain.Post;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class QueryFeedBySortOption {

    private final JPAQueryFactory queryFactory;

    public QueryFeedBySortOption(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public PageResponse<Post> findAllBySortOption(CursorPagination cursorPagination,
        FeedSortOption feedSortOption) {
        int pageSize = cursorPagination.pageSize();

        List<Post> queryResult = queryFactory
            .selectFrom(post)
            .where(cursorCondition(cursorPagination, feedSortOption))
            .orderBy(feedSortOption.getOrderSpecifier())
            .limit(cursorPagination.pageSize() + 1)
            .fetch();

        boolean hasMore = queryResult.size() > pageSize;
        List<Post> data = getPostList(queryResult, pageSize, hasMore);
        Long nextCursor = getNextCursor(queryResult, pageSize, hasMore);

        return new PageResponse<>(data, nextCursor, (long) queryResult.size(), hasMore);
    }

    private BooleanExpression cursorCondition(CursorPagination cursorPagination,
        FeedSortOption feedSortOption) {
        if (cursorPagination.lastFetchedId() == null) {
            return null;
        }

        Post lastPost = queryFactory.selectFrom(post)
            .where(post.id.eq(cursorPagination.lastFetchedId()))
            .fetchOne();

        if (lastPost == null) {
            return null;
        }

        return feedSortOption.getQueryCondition(lastPost);
    }

    private List<Post> getPostList(List<Post> queryResult, int pageSize, boolean hasMore) {
        if (!hasMore) {
            return queryResult;
        }
        return queryResult.subList(0, pageSize);
    }

    private Long getNextCursor(List<Post> queryResult, int pageSize, boolean hasMore) {
        if (!hasMore) {
            return null;
        }
        return queryResult.get(pageSize).getId();
    }
}
