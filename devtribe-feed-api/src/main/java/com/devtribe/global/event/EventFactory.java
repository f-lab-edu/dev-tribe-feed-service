package com.devtribe.global.event;

public interface EventFactory<T extends Event> {
    T makeEvent();
}
