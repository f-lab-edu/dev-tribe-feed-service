package com.devtribe.domain.post.application.dtos;

import com.devtribe.domain.post.dto.PostSortCriteria;
import java.time.LocalDateTime;

public record PostQueryRequest(
    LocalDateTime startDate,
    LocalDateTime endDate,
    Long authorId,
    PostSortCriteria sort,
    Integer page,
    Integer size
) {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;

    public PostQueryRequest {
        if (page == null) page = DEFAULT_PAGE;
        if (size == null) size = DEFAULT_SIZE;
        if (sort == null) sort = PostSortCriteria.NEWEST;
    }
}
