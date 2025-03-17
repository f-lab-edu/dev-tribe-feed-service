package com.devtribe.devtribe_feed_service.post.application;

import com.devtribe.devtribe_feed_service.global.common.CursorPagination;
import com.devtribe.devtribe_feed_service.global.common.PageResponse;
import com.devtribe.devtribe_feed_service.post.application.dtos.PostResponse;
import com.devtribe.devtribe_feed_service.post.application.interfaces.FeedRepository;
import com.devtribe.devtribe_feed_service.post.application.validators.GetFeedRequestValidator;
import com.devtribe.devtribe_feed_service.post.domain.FeedSortOption;
import com.devtribe.devtribe_feed_service.post.domain.Post;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeedService {

    private final FeedRepository feedRepository;
    private final GetFeedRequestValidator getFeedRequestValidator;

    public FeedService(FeedRepository feedRepository, GetFeedRequestValidator getFeedRequestValidator) {
        this.feedRepository = feedRepository;
        this.getFeedRequestValidator = getFeedRequestValidator;
    }

    @Transactional(readOnly = true)
    public PageResponse<PostResponse> getFeedListBySortOption(CursorPagination cursorPagination, FeedSortOption sort) {
        getFeedRequestValidator.validateCursorPagination(cursorPagination);
        getFeedRequestValidator.validateSortOption(sort);

        if (cursorPagination == null) {
            cursorPagination = CursorPagination.defaultCursorPagination();
        }

        PageResponse<Post> postPageResponse = feedRepository.findAllBySortOption(cursorPagination, sort);

        return new PageResponse<>(
            convertToPostResponses(postPageResponse.data()),
            postPageResponse.nextCursor(),
            postPageResponse.totalCount(),
            postPageResponse.hasNextPage()
        );
    }

    private List<PostResponse> convertToPostResponses(List<Post> posts) {
        return posts.stream().map(PostResponse::from).toList();
    }
}
