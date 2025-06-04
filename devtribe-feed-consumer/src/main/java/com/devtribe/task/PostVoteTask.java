package com.devtribe.task;

import com.devtribe.domain.vote.dao.PostVoteRedisRepository;
import com.devtribe.event.PostVoteEvent;
import org.springframework.stereotype.Component;

@Component
public class PostVoteTask {

    private final PostVoteRedisRepository postVoteRedisRepository;

    public PostVoteTask(PostVoteRedisRepository postVoteRedisRepository) {
        this.postVoteRedisRepository = postVoteRedisRepository;
    }

    public void processEvent(PostVoteEvent event) {
        postVoteRedisRepository.vote(
            event.getPostId(),
            event.getUserId(),
            event.getVoteType()
        );
    }
}
