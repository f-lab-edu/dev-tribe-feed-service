package com.devtribe.domain.post.dto;

import com.devtribe.global.model.PageRequest;

public record PostSearchCriteria(
    String keyword,
    PageRequest pageRequest
) {

}
