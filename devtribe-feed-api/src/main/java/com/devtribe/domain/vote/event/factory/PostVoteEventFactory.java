package com.devtribe.domain.vote.event.factory;

import com.devtribe.domain.vote.event.PostVoteEvent;
import com.devtribe.global.event.EventFactory;

public interface PostVoteEventFactory extends EventFactory<PostVoteEvent> {

    default PostVoteEvent createEvent(Long postId, Long userId) {
        PostVoteEvent event = makeEvent();
        event.setVoteInfo(postId, userId);
        return event;
    }
}
