package com.devtribe.devtribe_feed_service.post.domain.vote;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@Entity
@Table(name = "vote")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "target_type", nullable = false)
    private TargetType targetType;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "vote_type", nullable = false)
    private VoteType voteType;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public Vote(Long targetId, Long userId, TargetType targetType, VoteType voteType) {
        this.targetId = targetId;
        this.userId = userId;
        this.targetType = targetType;
        this.voteType = voteType;
    }
}
