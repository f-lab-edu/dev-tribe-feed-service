package com.devtribe.devtribe_feed_service.test.fixtures

import com.devtribe.devtribe_feed_service.post.domain.ContentSource
import com.devtribe.devtribe_feed_service.post.domain.Post
import com.devtribe.devtribe_feed_service.post.domain.Publication

class PostFixture {

    static Post getPost(Long postId) {
        new Post(id: postId)
    }

    static Post getPost(Long postId, Long userId, String title = "title", String content = "content", ContentSource contentSource, String thumbnail = "thumbnail", Publication publication = Publication.PUBLIC) {
        new Post(
                id: postId,
                userId: userId,
                title: title,
                content: content,
                contentSource: contentSource,
                thumbnail: thumbnail,
                publication: publication
        )
    }

    static Post getUpdatedPost(Long postId, Long userId, ContentSource contentSource, String title = "title updated", String content = "content updated", String thumbnail = "thumbnail updated", Publication publication = Publication.PRIVATE) {
        new Post(
                id: postId,
                userId: userId,
                title: title,
                content: content,
                contentSource: contentSource,
                thumbnail: thumbnail,
                publication: publication
        )
    }
}
