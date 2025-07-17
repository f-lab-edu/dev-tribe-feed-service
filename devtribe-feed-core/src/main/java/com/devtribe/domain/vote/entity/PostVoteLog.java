package com.devtribe.domain.vote.entity;

import com.devtribe.global.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "post_vote_log")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostVoteLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "vote_type", nullable = false)
    private VoteType voteType;

    @Builder
    public PostVoteLog(Long postId, Long userId, VoteType voteType) {
        this.postId = postId;
        this.userId = userId;
        this.voteType = voteType;
    }
}
