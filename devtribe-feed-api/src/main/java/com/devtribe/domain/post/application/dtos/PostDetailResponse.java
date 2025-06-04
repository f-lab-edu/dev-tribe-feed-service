package com.devtribe.domain.post.application.dtos;

import com.devtribe.domain.post.entity.Post;
import com.devtribe.domain.tag.appliction.dtos.TagResponse;
import java.util.List;

public record PostDetailResponse(
    Long postId,
    String title,
    String content,
    String thumbnail,
    List<TagResponse> tags
) {

    public static PostDetailResponse of(final Post post, final List<TagResponse> tags) {
        return new PostDetailResponse(
            post.getId(),
            post.getTitle(),
            post.getContent(),
            post.getThumbnail(),
            tags
        );
    }
}
