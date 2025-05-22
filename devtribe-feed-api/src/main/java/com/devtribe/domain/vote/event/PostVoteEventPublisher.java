package com.devtribe.domain.vote.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PostVoteEventPublisher {

    private final StreamBridge streamBridge;

    public PostVoteEventPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void publish(PostVoteEvent payload) {
        streamBridge.send("vote-out-0", payload);
        log.info("Published post-vote event: {}", payload);
    }
}
