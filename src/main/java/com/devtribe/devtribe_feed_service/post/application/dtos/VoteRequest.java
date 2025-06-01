package com.devtribe.devtribe_feed_service.post.application.dtos;

import com.devtribe.devtribe_feed_service.post.domain.vote.PostVote;
import com.devtribe.devtribe_feed_service.post.domain.vote.VoteType;

public record VoteRequest(
    Long userId,
    VoteType voteType
) {

    public static VoteRequest upvotePostRequest(Long userId) {
        return new VoteRequest(userId, VoteType.UPVOTE);
    }

    public static VoteRequest downvotePostRequest(Long userId) {
        return new VoteRequest(userId, VoteType.DOWNVOTE);
    }

    public PostVote toEntity() {
        return PostVote.builder()
            .userId(this.userId)
            .voteType(this.voteType)
            .build();
    }
}
