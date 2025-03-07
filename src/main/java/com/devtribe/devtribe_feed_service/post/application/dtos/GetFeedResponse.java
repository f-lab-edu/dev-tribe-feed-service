package com.devtribe.devtribe_feed_service.post.application.dtos;

import com.devtribe.devtribe_feed_service.global.common.CursorMetadata;
import java.util.List;

public record GetFeedResponse(List<PostResponse> data, CursorMetadata cursorMetaData) {

}
