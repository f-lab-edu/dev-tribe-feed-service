package com.devtribe.event;

import com.devtribe.domain.vote.entity.VoteType;
import java.time.LocalDateTime;

public record PostVoteEvent(
    VoteType voteType,
    Long userId,
    Long postId,
    LocalDateTime occurredAt
) {


}
