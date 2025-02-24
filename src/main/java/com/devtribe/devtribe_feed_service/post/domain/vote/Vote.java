package com.devtribe.devtribe_feed_service.post.domain.vote;

import lombok.Builder;

public class Vote {

    private Long id;
    private Long targetId;
    private Long userId;
    private String targetType;
    private String voteType;

    @Builder
    public Vote(Long targetId, Long userId, TargetType targetType, VoteType voteType) {
        this.targetId = targetId;
        this.userId = userId;
        this.targetType = targetType.name();
        this.voteType = voteType.name();
    }
}
