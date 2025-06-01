package com.devtribe.domain.vote.application.dtos;

import com.devtribe.domain.vote.entity.PostVote;
import com.devtribe.domain.vote.entity.VoteType;

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
