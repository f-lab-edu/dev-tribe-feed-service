package com.devtribe.devtribe_feed_service.post.application;

import com.devtribe.devtribe_feed_service.global.common.CursorMetadata;
import com.devtribe.devtribe_feed_service.global.common.CursorPagination;
import com.devtribe.devtribe_feed_service.global.common.PageResponse;
import com.devtribe.devtribe_feed_service.post.application.dtos.GetFeedResponse;
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
    public GetFeedResponse getFeedListBySortOption(CursorPagination cursorPagination, String sort) {
        getFeedRequestValidator.validateCursorPagination(cursorPagination);
        getFeedRequestValidator.validateSortOption(sort);

        if (cursorPagination == null) {
            cursorPagination = CursorPagination.defaultCursorPagination();
        }

        PageResponse<Post> postPageResponse = feedRepository.findAllBySortOption(
            cursorPagination,
            FeedSortOption.fromValue(sort)
        );

        return new GetFeedResponse(
            convertToPostResponses(postPageResponse.data()),
            createCursorMetadata(postPageResponse)
        );
    }

    private List<PostResponse> convertToPostResponses(List<Post> posts) {
        return posts.stream().map(PostResponse::from).toList();
    }

    private CursorMetadata createCursorMetadata(PageResponse<Post> postPageResponse) {
        return new CursorMetadata(
            postPageResponse.nextCursor(),
            postPageResponse.totalCount(),
            postPageResponse.hasNextPage()
        );
    }
}
