package com.devtribe.domain.vote.api;

import com.devtribe.domain.vote.application.VoteService;
import com.devtribe.domain.vote.application.dtos.PostVoteResponse;
import com.devtribe.domain.vote.application.dtos.VoteRequest;
import com.devtribe.global.security.CustomUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/votes")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping("/post/{id}")
    public PostVoteResponse voteCount(
        @PathVariable("id") Long postId
    ) {
        return voteService.getVoteCount(postId);
    }

    @PostMapping("post/{id}")
    public PostVoteResponse vote(
        @PathVariable("id") Long postId,
        @RequestBody VoteRequest voteType,
        @AuthenticationPrincipal CustomUserDetail userDetail
    ) {
        log.info("voteType: {}", voteType);
        return voteService.vote(postId, userDetail.getUserId(), voteType);
    }

    @DeleteMapping("post/{id}")
    public ResponseEntity<Void> unvote(
        @PathVariable("id") Long postId,
        @AuthenticationPrincipal CustomUserDetail userDetail
    ) {
        Long userId = userDetail.getUserId();
        voteService.unvote(postId, userId);
        return ResponseEntity.noContent().build();
    }
}
