package com.devtribe.domain.post.api;

import com.devtribe.domain.post.application.PostSearchService;
import com.devtribe.domain.post.application.dtos.PostResponse;
import com.devtribe.global.model.FeedSearchRequest;
import com.devtribe.global.model.PageResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/feeds")
public class FeedController {

    private final PostSearchService postSearchService;

    public FeedController(PostSearchService postSearchService) {
        this.postSearchService = postSearchService;
    }

    @PostMapping("/search")
    public PageResponse<PostResponse> findFeedBySearchOption(
        @RequestBody FeedSearchRequest feedSearchRequest
    ) {
        return postSearchService.findFeedBySearchOption(feedSearchRequest);
    }
}
