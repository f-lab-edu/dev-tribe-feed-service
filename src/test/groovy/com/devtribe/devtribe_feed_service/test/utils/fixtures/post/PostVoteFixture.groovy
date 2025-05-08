package com.devtribe.devtribe_feed_service.test.utils.fixtures.post

import com.devtribe.devtribe_feed_service.post.domain.vote.PostVote
import com.devtribe.devtribe_feed_service.post.domain.vote.VoteType

import java.time.LocalDateTime

class PostVoteFixture {
    static PostVote createPostVote(Map map = [:]) {
        new PostVote(
                id: map.getOrDefault("id", null) as Long,
                userId: map.getOrDefault("userId", 1L) as Long,
                postId: map.getOrDefault("userId", 1L) as Long,
                voteType: map.getOrDefault("voteType", VoteType.UPVOTE) as VoteType,
                createdAt: map.getOrDefault("createdAt", LocalDateTime.now()) as LocalDateTime,
                updatedAt: map.getOrDefault("updatedAt", null) as LocalDateTime,
                createdBy: map.getOrDefault("createdBy", 1L) as Long,
                updatedBy: map.getOrDefault("updatedBy", null as Long)
        )
    }
}
