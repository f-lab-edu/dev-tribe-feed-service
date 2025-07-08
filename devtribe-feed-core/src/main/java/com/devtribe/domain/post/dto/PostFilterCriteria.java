package com.devtribe.domain.post.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PostFilterCriteria {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long authorId;

    public PostFilterCriteria() {
    }

    public PostFilterCriteria(LocalDateTime startDate, LocalDateTime endDate, Long authorId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.authorId = authorId;
    }
}
