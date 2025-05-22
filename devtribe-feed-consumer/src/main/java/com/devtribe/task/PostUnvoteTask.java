package com.devtribe.task;

import com.devtribe.event.PostVoteEvent;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PostUnvoteTask extends AbstractVoteTask {

    public PostUnvoteTask(
        RedisScript<List> postUnvoteScript,
        RedisTemplate<String, String> redisTemplate,
        RedisScript<List> postVoteScript
    ) {
        super(postUnvoteScript, redisTemplate, postVoteScript);
    }

    public void processEvent(PostVoteEvent event) {
        unvote(
            event.getPostId(),
            event.getUserId()
        );

        log.info("event received: {}", event);
    }
}
