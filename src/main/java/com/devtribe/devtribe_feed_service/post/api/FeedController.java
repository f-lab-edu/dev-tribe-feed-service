package com.devtribe.devtribe_feed_service.post.api;

import com.devtribe.devtribe_feed_service.global.common.PageResponse;
import com.devtribe.devtribe_feed_service.post.application.FeedService;
import com.devtribe.devtribe_feed_service.post.application.dtos.FeedSearchRequest;
import com.devtribe.devtribe_feed_service.post.application.dtos.PostResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/feeds")
public class FeedController {

    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @PostMapping("/search")
    public PageResponse<PostResponse> findFeedBySearchOption(
        @RequestBody FeedSearchRequest feedSearchRequest
    ) {
        return feedService.findFeedBySearchOption(feedSearchRequest);
    }
}
