package com.devtribe.global.model;

public record PageRequest(
    int page,
    int size
) {

    public int getOffset() {
        return page * size;
    }
}
