package com.devtribe.devtribe_feed_service.post.api;

import com.devtribe.devtribe_feed_service.global.common.CursorPagination;
import com.devtribe.devtribe_feed_service.global.common.PageResponse;
import com.devtribe.devtribe_feed_service.post.application.FeedService;
import com.devtribe.devtribe_feed_service.post.application.dtos.PostResponse;
import com.devtribe.devtribe_feed_service.post.domain.FeedSortOption;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/feeds")
public class FeedController {

    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping
    public PageResponse<PostResponse> getFeedListBySortOption(
        @RequestParam(required = false) CursorPagination cursorPagination,
        @RequestParam(defaultValue = "BY_NEWEST") FeedSortOption sort
    ) {
        return feedService.getFeedListBySortOption(cursorPagination, sort);
    }
}
