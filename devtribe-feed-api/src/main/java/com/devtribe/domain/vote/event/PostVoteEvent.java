package com.devtribe.domain.vote.event;

import com.devtribe.domain.vote.entity.VoteType;
import java.time.LocalDateTime;

public record PostVoteEvent(VoteType type, Long userId, Long postId, LocalDateTime createdAt) {

}
