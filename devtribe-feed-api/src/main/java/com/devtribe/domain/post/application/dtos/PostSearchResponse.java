package com.devtribe.domain.post.application.dtos;

import java.util.List;
import lombok.Getter;

@Getter
public class PostSearchResponse {

    private final List<PostResponse> results;

    public PostSearchResponse(List<PostResponse> posts) {
        this.results = posts;
    }
}
