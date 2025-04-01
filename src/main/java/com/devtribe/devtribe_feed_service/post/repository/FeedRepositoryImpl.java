package com.devtribe.devtribe_feed_service.post.repository;

import static com.devtribe.devtribe_feed_service.post.domain.QPost.post;

import com.devtribe.devtribe_feed_service.global.common.PageResponse;
import com.devtribe.devtribe_feed_service.post.application.dtos.FeedSearchRequest;
import com.devtribe.devtribe_feed_service.post.application.interfaces.FeedRepository;
import com.devtribe.devtribe_feed_service.post.domain.FeedFilterOption;
import com.devtribe.devtribe_feed_service.post.domain.FeedSortOption;
import com.devtribe.devtribe_feed_service.post.domain.Post;
import com.devtribe.devtribe_feed_service.post.repository.query.SortQuery;
import com.devtribe.devtribe_feed_service.post.repository.query.SortQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class FeedRepositoryImpl implements FeedRepository {

    private final SortQueryFactory sortQueryFactory;
    private final JPAQueryFactory queryFactory;

    public FeedRepositoryImpl(SortQueryFactory sortQueryFactory, JPAQueryFactory queryFactory) {
        this.sortQueryFactory = sortQueryFactory;
        this.queryFactory = queryFactory;
    }

    @Override
    public PageResponse<Post> findFeedsByFilterAndSortOption(FeedSearchRequest feedSearchRequest) {
        FeedSortOption feedSortOption = feedSearchRequest.getFeedSortOption();
        FeedFilterOption feedFilterOption = feedSearchRequest.getFeedFilterOption();
        int offset = feedSearchRequest.getOffset();
        int size = feedSearchRequest.getSize();

        SortQuery sortQuery = sortQueryFactory.getSortQuery(feedSortOption);

        List<Post> queryResult = queryFactory
            .selectFrom(post)
            .where(
                /// feedFilterOption 추가
            )
            .orderBy(sortQuery.getOrderBy())
            .offset(offset)
            .limit((long) offset * size)
            .fetch();

        return new PageResponse<>(queryResult, offset);
    }
}
