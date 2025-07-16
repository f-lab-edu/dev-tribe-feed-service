package com.devtribe.domain.post.dto;

import com.devtribe.global.model.PageRequest;

public record PostQueryCriteria(
    PostFilterCriteria filterCriteria,
    PostSortCriteria sortCriteria,
    PageRequest pageRequest
) {

}
