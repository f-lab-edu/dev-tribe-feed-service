package com.devtribe.domain.vote.event.factory;

import com.devtribe.domain.vote.event.PostUnvoteEvent;
import com.devtribe.domain.vote.event.PostVoteEvent;
import org.springframework.stereotype.Service;

@Service
public class PostUnvoteEventFactory implements PostVoteEventFactory {

    @Override
    public PostVoteEvent makeEvent() {
        return new PostUnvoteEvent();
    }
}
