package com.devtribe.domain.vote.event;

import com.devtribe.domain.vote.entity.VoteType;

public class PostDownvoteEvent extends PostVoteEvent{

    public PostDownvoteEvent() {
        this.voteType = VoteType.DOWNVOTE;
    }
}
