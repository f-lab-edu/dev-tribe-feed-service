package com.devtribe.devtribe_feed_service.post.application.dtos;

import com.devtribe.devtribe_feed_service.post.domain.vote.PostVote;
import com.devtribe.devtribe_feed_service.post.domain.vote.VoteType;

public record VoteRequest(
    Long userId,
    Long postId,
    VoteType voteType
) {

    public static VoteRequest upvotePostRequest(Long userId, Long postId) {
        return new VoteRequest(userId, postId, VoteType.UPVOTE);
    }

    public static VoteRequest downvotePostRequest(Long userId, Long postId) {
        return new VoteRequest(userId, postId, VoteType.DOWNVOTE);
    }

    public PostVote toEntity() {
        return PostVote.builder()
            .userId(this.userId)
            .postId(this.postId)
            .voteType(this.voteType)
            .build();
    }
}
