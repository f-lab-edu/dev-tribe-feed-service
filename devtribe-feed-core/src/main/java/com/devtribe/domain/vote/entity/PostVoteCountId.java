package com.devtribe.domain.vote.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.io.Serializable;
import lombok.Getter;

@Getter
@Embeddable
public class PostVoteCountId implements Serializable {

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "vote_type", nullable = false)
    private VoteType voteType;
}
