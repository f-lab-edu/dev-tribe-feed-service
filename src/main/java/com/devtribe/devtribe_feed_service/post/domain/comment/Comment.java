package com.devtribe.devtribe_feed_service.post.domain.comment;

import com.devtribe.devtribe_feed_service.post.domain.Post;
import com.devtribe.devtribe_feed_service.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    private Long id;
    private Post post;
    private User user;
    private String content;
    private Integer upvoteCount;
    private Integer downvoteCount;

    @Builder
    public Comment(Post post, User user, String content) {
        this.post = post;
        this.user = user;
        this.content = content;
        this.upvoteCount = 0;
        this.downvoteCount = 0;
    }
}
