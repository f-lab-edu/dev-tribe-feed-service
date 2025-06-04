package com.devtribe.task;

import com.devtribe.domain.vote.dao.PostVoteRedisRepository;
import com.devtribe.event.PostVoteEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PostUnvoteTask {

    private final PostVoteRedisRepository postVoteRedisRepository;

    public PostUnvoteTask(PostVoteRedisRepository postVoteRedisRepository) {
        this.postVoteRedisRepository = postVoteRedisRepository;
    }

    public void processEvent(PostVoteEvent event) {
        postVoteRedisRepository.unvote(
            event.getPostId(),
            event.getUserId()
        );

        log.info("event received: {}", event);
    }
}
