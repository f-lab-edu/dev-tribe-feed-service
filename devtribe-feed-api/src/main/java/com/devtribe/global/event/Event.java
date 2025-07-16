package com.devtribe.global.event;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public abstract class Event {

    private final LocalDateTime occurredAt;

    public Event() {
        this.occurredAt = LocalDateTime.now();
    }
}
