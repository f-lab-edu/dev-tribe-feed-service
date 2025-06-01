package com.devtribe.domain.vote.application.dtos;

public record PostVoteResponse(Long postId, Integer upvoteCount, Integer downvoteCount) {

}
