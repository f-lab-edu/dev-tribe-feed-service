package com.devtribe.domain.vote.entity;

import com.devtribe.global.model.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "post_vote_count")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostVoteCount extends BaseTimeEntity {

    @EmbeddedId
    private PostVoteCountId id;

    @Column(name = "vote_count", nullable = false)
    private Integer voteCount;

}

