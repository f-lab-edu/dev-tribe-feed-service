package com.devtribe.domain.post.application.dtos;

import java.util.List;
import lombok.Getter;

@Getter
public class PostQueryResponse {

    private final List<PostResponse> results;
    private final int page;

    public PostQueryResponse(List<PostResponse> posts, int page) {
        this.results = posts;
        this.page = page;
    }
}
