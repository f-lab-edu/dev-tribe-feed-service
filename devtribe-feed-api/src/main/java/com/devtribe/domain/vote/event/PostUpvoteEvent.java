package com.devtribe.domain.vote.event;

import com.devtribe.domain.vote.entity.VoteType;

public class PostUpvoteEvent extends PostVoteEvent{

    public PostUpvoteEvent() {
        this.voteType = VoteType.UPVOTE;
    }
}
