package com.devtribe.event;

import com.devtribe.domain.vote.entity.VoteType;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PostVoteEvent {

    private VoteType voteType;
    private Long userId;
    private Long postId;
    private LocalDateTime occurredAt;

    @Override
    public String toString() {
        return "VoteEvent{" +
            "voteType=" + voteType +
            ", userId=" + userId +
            ", postId=" + postId +
            ", occurredAt=" + occurredAt +
            '}';
    }
}
