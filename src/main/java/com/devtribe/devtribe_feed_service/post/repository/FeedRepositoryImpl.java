package com.devtribe.devtribe_feed_service.post.repository;

import com.devtribe.devtribe_feed_service.global.common.CursorPagination;
import com.devtribe.devtribe_feed_service.global.common.PageResponse;
import com.devtribe.devtribe_feed_service.post.application.interfaces.FeedRepository;
import com.devtribe.devtribe_feed_service.post.domain.FeedSortOption;
import com.devtribe.devtribe_feed_service.post.domain.Post;
import com.devtribe.devtribe_feed_service.post.repository.query.QueryFeedBySortOption;
import org.springframework.stereotype.Repository;

@Repository
public class FeedRepositoryImpl implements FeedRepository {

    private final QueryFeedBySortOption queryFeedBySortOption;

    public FeedRepositoryImpl(QueryFeedBySortOption queryFeedBySortOption) {
        this.queryFeedBySortOption = queryFeedBySortOption;
    }

    @Override
    public PageResponse<Post> findAllBySortOption(
        CursorPagination cursorPagination,
        FeedSortOption feedSortOption
    ) {
        return this.queryFeedBySortOption.findAllBySortOption(
            cursorPagination.cursorId(),
            cursorPagination.pageSize(),
            feedSortOption);
    }
}
