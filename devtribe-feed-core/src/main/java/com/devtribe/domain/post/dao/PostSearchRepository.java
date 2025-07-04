package com.devtribe.domain.post.dao;

import com.devtribe.domain.post.entity.Post;
import com.devtribe.global.model.FeedSearchRequest;
import com.devtribe.global.model.PageResponse;
import org.springframework.stereotype.Repository;

@Repository
public interface PostSearchRepository {

    PageResponse<Post> findFeedsByFilterAndSortOption(FeedSearchRequest feedSearchRequest);
}
