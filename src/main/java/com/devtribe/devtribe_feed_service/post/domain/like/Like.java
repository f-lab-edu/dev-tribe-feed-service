package com.devtribe.devtribe_feed_service.post.domain.like;

import com.devtribe.devtribe_feed_service.post.domain.Post;
import com.devtribe.devtribe_feed_service.post.domain.comment.Comment;

public class Like {

    private Long id;
    private Long targetId;
    private Long userId;
    private String likeType;

    public Like(Post post, Long userId) {
        this.targetId = post.getId();
        this.userId = userId;
        this.likeType = LikeType.POST.name();
    }

    public Like(Comment comment, Long userId) {
        this.targetId = comment.getId();
        this.userId = userId;
        this.likeType = LikeType.COMMENT.name();
    }
}
