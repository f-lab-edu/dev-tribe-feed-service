package com.devtribe.domain.vote.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@Entity
@Table(name = "post_vote_count")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostVoteCount {

    @EmbeddedId
    private PostVoteCountId id;

    @Column(name = "vote_count", nullable = false)
    private Integer voteCount;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}

