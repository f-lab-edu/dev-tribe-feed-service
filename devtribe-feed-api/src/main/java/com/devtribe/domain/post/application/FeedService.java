package com.devtribe.domain.post.application;

import com.devtribe.domain.post.application.dtos.PostResponse;
import com.devtribe.domain.post.application.validators.FeedRequestValidator;
import com.devtribe.domain.post.dao.FeedRepository;
import com.devtribe.domain.post.entity.Post;
import com.devtribe.global.model.FeedSearchRequest;
import com.devtribe.global.model.PageResponse;
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
        feedRequestValidator.validateSortOption(feedSearchRequest.feedSortOption());
        feedRequestValidator.validateFilterOption(feedSearchRequest.feedFilterOption());
        feedRequestValidator.validatePagination(feedSearchRequest.size());

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
