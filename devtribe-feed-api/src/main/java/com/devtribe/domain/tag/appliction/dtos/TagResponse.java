package com.devtribe.domain.tag.appliction.dtos;

import com.devtribe.domain.tag.entity.Tag;

public record TagResponse(String name) {

    public static TagResponse from(Tag tag) {
        return new TagResponse(tag.getName());
    }
}
