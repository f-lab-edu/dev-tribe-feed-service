package com.devtribe.domain.vote.event.factory;

import com.devtribe.domain.vote.event.PostUpvoteEvent;
import com.devtribe.domain.vote.event.PostVoteEvent;
import org.springframework.stereotype.Service;

@Service
public class PostUpvoteEventFactory implements PostVoteEventFactory {

    @Override
    public PostVoteEvent makeEvent() {
        return new PostUpvoteEvent();
    }
}
