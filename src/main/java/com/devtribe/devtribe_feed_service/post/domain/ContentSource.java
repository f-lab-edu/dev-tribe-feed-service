package com.devtribe.devtribe_feed_service.post.domain;

public class ContentSource {

    private Long contentId;
    private String contentType;

    public ContentSource(Long contentId, ContentType contentType) {
        this.contentId = contentId;
        this.contentType = contentType.name();
    }
}
