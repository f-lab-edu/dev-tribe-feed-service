package com.devtribe.domain.post.api;

import com.devtribe.domain.post.application.PostSearchService;
import com.devtribe.domain.post.application.dtos.PostSearchRequest;
import com.devtribe.domain.post.application.dtos.PostSearchResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
public class PostSearchController {

    private final PostSearchService postSearchService;

    public PostSearchController(PostSearchService postSearchService) {
        this.postSearchService = postSearchService;
    }

    @GetMapping("/search")
    public PostSearchResponse searchPostByKeyword(
        @ModelAttribute PostSearchRequest searchRequest
    ) {
        return postSearchService.searchPostByKeyword(searchRequest);
    }
}
