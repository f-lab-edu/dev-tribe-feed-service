package com.devtribe.domain.vote.application.dtos;

import com.devtribe.domain.vote.entity.VoteType;

public record VoteRequest(
    VoteType voteType
) {

}
