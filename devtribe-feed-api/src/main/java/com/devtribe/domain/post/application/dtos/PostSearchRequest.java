package com.devtribe.domain.post.application.dtos;

public record PostSearchRequest(String keyword, Integer page, Integer size) {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;

    public PostSearchRequest {
        if (page == null) page = DEFAULT_PAGE;
        if (size == null) size = DEFAULT_SIZE;
    }
}
