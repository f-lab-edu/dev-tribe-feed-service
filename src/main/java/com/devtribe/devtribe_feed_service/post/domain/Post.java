package com.devtribe.devtribe_feed_service.post.domain;

import com.devtribe.devtribe_feed_service.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    private Long id;
    private String title;
    private String content;
    private User author;
    private String thumbnail;
    private Publication publication;
    private Integer upvoteCount;
    private Integer downvoteCount;

    @Builder
    public Post(String title, String content, User author,
        String thumbnail) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.thumbnail = thumbnail;
        this.publication = Publication.PUBLIC;
        this.upvoteCount = 0;
        this.downvoteCount = 0;
    }
}
