package com.devtribe.devtribe_feed_service.test.utils.fixtures.post

import com.devtribe.devtribe_feed_service.post.domain.Post
import com.devtribe.devtribe_feed_service.post.domain.Publication

import java.time.LocalDateTime

class PostFixture {
    static Post createPost(Map map = [:]) {
        new Post(
                id: map.getOrDefault("id", null) as Long,
                userId: map.getOrDefault("userId", 1L) as Long,
                title: map.getOrDefault("title", "title") as String,
                content: map.getOrDefault("content", "content") as String,
                thumbnail: map.getOrDefault("thumbnail", "thumbnail") as String,
                publication: map.getOrDefault("publication", Publication.PUBLIC) as Publication,
                upvoteCount: map.getOrDefault("upvoteCount", 0) as Integer,
                downvoteCount: map.getOrDefault("downvoteCount", 0) as Integer,
                isDeleted: map.getOrDefault("isDelete", false) as Boolean,
                createdAt: map.getOrDefault("createdAt", LocalDateTime.now()) as LocalDateTime,
                updatedAt: map.getOrDefault("updatedAt", null) as LocalDateTime,
                createdBy: map.getOrDefault("createdBy", 1L) as Long,
                updatedBy: map.getOrDefault("updatedBy", null as Long)
        )
    }

    static Post getUpdatedPost(
            Long postId,
            Long userId,
            String title = "title updated",
            String content = "content updated",
            String thumbnail = "thumbnail updated",
            Publication publication = Publication.PRIVATE
    ) {
        new Post(
                id: postId,
                userId: userId,
                title: title,
                content: content,
                thumbnail: thumbnail,
                publication: publication
        )
    }
}
