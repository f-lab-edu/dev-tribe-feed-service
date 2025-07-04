package com.devtribe.domain.post.application.dtos;

import com.devtribe.global.model.PageRequest;

public record PostSearchRequest(String keyword, PageRequest pageRequest) {

}
