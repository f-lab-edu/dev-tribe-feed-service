package com.devtribe.domain.vote.event;

import com.devtribe.domain.vote.entity.VoteType;

public class PostUnvoteEvent extends PostVoteEvent{

    public PostUnvoteEvent() {
        this.voteType = VoteType.UNVOTE;
    }
}
