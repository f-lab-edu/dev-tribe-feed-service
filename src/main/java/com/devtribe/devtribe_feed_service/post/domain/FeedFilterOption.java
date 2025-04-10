package com.devtribe.devtribe_feed_service.post.domain;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class FeedFilterOption {

    private String keyword = null;
    private LocalDateTime startDate = null;
    private LocalDateTime endDate = null;
    private Long authorId = null;
}
