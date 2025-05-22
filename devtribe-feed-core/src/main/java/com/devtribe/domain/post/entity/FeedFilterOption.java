package com.devtribe.domain.post.entity;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class FeedFilterOption {

    private String keyword = null;
    private LocalDateTime startDate = null;
    private LocalDateTime endDate = null;
    private Long authorId = null;
}
