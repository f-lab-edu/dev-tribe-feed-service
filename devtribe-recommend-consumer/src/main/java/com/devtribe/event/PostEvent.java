package com.devtribe.event;

import com.devtribe.domain.post.entity.PostDocument;
import java.time.LocalDateTime;
import java.util.List;

public record PostEvent(
    PostEventType eventType,
    Long postId,
    List<String> tags,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public PostDocument toPostDocument() {
        return new PostDocument(
            this.postId,
            this.tags,
            this.createdAt,
            this.updatedAt
        );
    }
}
