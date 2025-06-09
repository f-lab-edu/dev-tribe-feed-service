package com.devtribe.task;

import com.devtribe.domain.vote.dao.PostVoteRedisRepository;
import com.devtribe.event.PostVoteEvent;
import org.springframework.stereotype.Component;

@Component
public class PostUnvoteTask {

    private final PostVoteRedisRepository postVoteRedisRepository;

    public PostUnvoteTask(PostVoteRedisRepository postVoteRedisRepository) {
        this.postVoteRedisRepository = postVoteRedisRepository;
    }

    public void processEvent(PostVoteEvent event) {
        postVoteRedisRepository.unvote(
            event.postId(),
            event.userId()
        );
    }
}
