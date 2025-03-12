package com.devtribe.devtribe_feed_service.global.common;


public record CursorMetadata(Long nextCursor, Long totalCount, boolean hasNextPage) {

}
