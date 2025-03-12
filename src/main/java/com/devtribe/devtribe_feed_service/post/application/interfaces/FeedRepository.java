package com.devtribe.devtribe_feed_service.post.application.interfaces;

import com.devtribe.devtribe_feed_service.global.common.CursorPagination;
import com.devtribe.devtribe_feed_service.global.common.PageResponse;
import com.devtribe.devtribe_feed_service.post.domain.FeedSortOption;
import com.devtribe.devtribe_feed_service.post.domain.Post;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRepository {

    PageResponse<Post> findAllBySortOption(CursorPagination cursorPagination, FeedSortOption feedSortOption);
}
