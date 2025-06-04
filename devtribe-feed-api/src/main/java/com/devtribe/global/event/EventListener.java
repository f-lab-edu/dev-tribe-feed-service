package com.devtribe.global.event;

import com.devtribe.domain.vote.event.PostVoteEvent;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class EventListener {

    private final StreamBridge streamBridge;

    public EventListener(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publish(PostVoteEvent payload) {
        streamBridge.send("vote-out-0", payload);
    }
}
