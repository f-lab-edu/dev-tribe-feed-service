package com.devtribe.devtribe_feed_service.global.common;

public record CursorPagination(
    Long cursorId,
    Integer pageSize
) {

    private static final int DEFAULT_PAG_SIZE = 10;
    private static final int MAX_PAG_SIZE = 30;
    private static final int MIN_PAG_SIZE = 1;

    public CursorPagination(Long cursorId, Integer pageSize) {
        this.cursorId = cursorId;
        this.pageSize = pageSize == null ? DEFAULT_PAG_SIZE : pageSize;
    }

    public boolean isPageSizeInRange() {
        return this.pageSize <= MAX_PAG_SIZE && this.pageSize >= MIN_PAG_SIZE;
    }
}
