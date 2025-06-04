package com.devtribe.domain.vote.event;

import com.devtribe.domain.vote.entity.VoteType;
import com.devtribe.global.event.Event;
import lombok.Getter;

@Getter
public abstract class PostVoteEvent extends Event {

    protected VoteType voteType;
    protected Long userId;
    protected Long postId;

    public void setVoteInfo(Long postId, Long userId) {
        this.postId = postId;
        this.userId = userId;
    }
}
