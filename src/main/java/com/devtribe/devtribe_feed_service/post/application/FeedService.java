package com.devtribe.devtribe_feed_service.post.application;

import com.devtribe.devtribe_feed_service.global.common.PageResponse;
import com.devtribe.devtribe_feed_service.post.application.dtos.FeedSearchRequest;
import com.devtribe.devtribe_feed_service.post.application.dtos.PostResponse;
import com.devtribe.devtribe_feed_service.post.application.interfaces.FeedRepository;
import com.devtribe.devtribe_feed_service.post.application.validators.FeedRequestValidator;
import com.devtribe.devtribe_feed_service.post.domain.Post;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeedService {

    private final FeedRepository feedRepository;
    private final FeedRequestValidator feedRequestValidator;

    public FeedService(FeedRepository feedRepository, FeedRequestValidator feedRequestValidator) {
        this.feedRepository = feedRepository;
        this.feedRequestValidator = feedRequestValidator;
    }

    @Transactional(readOnly = true)
    public PageResponse<PostResponse> findFeedBySearchOption(FeedSearchRequest feedSearchRequest) {
        feedRequestValidator.validatePagination(feedSearchRequest.getSize());
        feedRequestValidator.validateSortOption(feedSearchRequest.getFeedSortOption());

        PageResponse<Post> postPageResponse = feedRepository.findFeedsByFilterAndSortOption(feedSearchRequest);

        return new PageResponse<>(
            convertToPostResponses(postPageResponse.data()),
            postPageResponse.pageNo()
        );
    }

    private List<PostResponse> convertToPostResponses(List<Post> posts) {
        return posts.stream().map(PostResponse::from).toList();
    }
}
