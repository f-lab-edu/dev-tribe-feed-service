package com.devtribe.devtribe_feed_service.post.domain;

import com.devtribe.devtribe_feed_service.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Post {

    private Long id;
    private String title;
    private String content;
    private User author;
    private ContentSource contentSource;
    private String thumbnail;
    private Publication publication;
    private Integer upvoteCount;
    private Integer downvoteCount;

    @Builder
    public Post(String title, String content, User author, ContentSource contentSource,
        String thumbnail) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.thumbnail = thumbnail;
        this.contentSource = contentSource;
        this.publication = Publication.PUBLIC;
        this.upvoteCount = 0;
        this.downvoteCount = 0;
    }
}
