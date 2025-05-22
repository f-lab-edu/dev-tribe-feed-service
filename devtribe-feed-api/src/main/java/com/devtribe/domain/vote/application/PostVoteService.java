package com.devtribe.domain.vote.application;

import com.devtribe.domain.post.application.PostService;
import com.devtribe.domain.post.entity.Post;
import com.devtribe.domain.vote.application.dtos.VoteRequest;
import com.devtribe.domain.vote.entity.VoteType;
import com.devtribe.domain.vote.event.PostVoteEvent;
import com.devtribe.domain.vote.event.PostVoteEventPublisher;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class PostVoteService {

    private final PostService postService;
    private final PostVoteEventPublisher postVoteEventPublisher;

    public PostVoteService(
        PostService postService,
        PostVoteEventPublisher postVoteEventPublisher
    ) {
        this.postService = postService;
        this.postVoteEventPublisher = postVoteEventPublisher;
    }

    public void vote(Long postId, Long userId, VoteRequest voteRequest) {
        Post post = postService.getPost(postId);

        postVoteEventPublisher.publish(
            new PostVoteEvent(
                voteRequest.voteType(),
                userId,
                post.getId(),
                LocalDateTime.now())
        );
    }

    public void unvote(Long postId, Long userId) {
        Post post = postService.getPost(postId);

        postVoteEventPublisher.publish(
            new PostVoteEvent(
                VoteType.UNVOTE,
                userId,
                post.getId(),
                LocalDateTime.now())
        );
    }
}
