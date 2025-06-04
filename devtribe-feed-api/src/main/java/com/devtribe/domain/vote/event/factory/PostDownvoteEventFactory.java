package com.devtribe.domain.vote.event.factory;

import com.devtribe.domain.vote.event.PostDownvoteEvent;
import com.devtribe.domain.vote.event.PostVoteEvent;
import org.springframework.stereotype.Service;

@Service
public class PostDownvoteEventFactory implements PostVoteEventFactory {

    @Override
    public PostVoteEvent makeEvent() {
        return new PostDownvoteEvent();
    }
}
