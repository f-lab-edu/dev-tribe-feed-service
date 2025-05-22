package com.devtribe.event;

import com.devtribe.domain.vote.entity.VoteType;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PostVoteEvent {

    private VoteType type;
    private Long userId;
    private Long postId;
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return "VoteEvent{" +
            "type=" + type +
            ", userId=" + userId +
            ", postId=" + postId +
            ", createdAt=" + createdAt +
            '}';
    }
}
