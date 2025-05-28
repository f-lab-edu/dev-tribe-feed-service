package com.devtribe.domain.post.application.dtos;

import com.devtribe.domain.post.entity.Publication;
import java.util.List;

public record UpdatePostRequest(
    Long authorId,
    String title,
    String content,
    String thumbnail,
    Publication publication,
    List<Long> tags
) {

}
