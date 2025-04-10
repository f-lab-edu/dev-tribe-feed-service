package com.devtribe.devtribe_feed_service.post.application.interfaces;

import com.devtribe.devtribe_feed_service.global.common.PageResponse;
import com.devtribe.devtribe_feed_service.post.application.dtos.FeedSearchRequest;
import com.devtribe.devtribe_feed_service.post.domain.Post;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRepository {

    PageResponse<Post> findFeedsByFilterAndSortOption(FeedSearchRequest feedSearchRequest);
}
