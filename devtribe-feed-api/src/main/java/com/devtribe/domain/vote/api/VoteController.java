package com.devtribe.domain.vote.api;

import com.devtribe.domain.vote.application.PostVoteService;
import com.devtribe.domain.vote.application.dtos.VoteRequest;
import com.devtribe.global.security.CustomUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/votes")
public class VoteController {

    private final PostVoteService postVoteService;

    public VoteController(PostVoteService postVoteService) {
        this.postVoteService = postVoteService;
    }

    @PostMapping("post/{id}")
    public void vote(
        @PathVariable("id") Long postId,
        @RequestBody VoteRequest voteType,
        @AuthenticationPrincipal CustomUserDetail userDetail
    ) {
        log.info("voteType: {}", voteType);
        postVoteService.vote(postId, userDetail.getUserId(), voteType);
    }

    @DeleteMapping("post/{id}")
    public void unvote(
        @PathVariable("id") Long postId,
        @AuthenticationPrincipal CustomUserDetail userDetail
    ) {
        Long userId = userDetail.getUserId();
        postVoteService.unvote(postId, userId);
    }
}
