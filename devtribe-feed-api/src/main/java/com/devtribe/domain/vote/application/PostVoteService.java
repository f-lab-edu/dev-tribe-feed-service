package com.devtribe.domain.vote.application;

import com.devtribe.domain.post.application.PostService;
import com.devtribe.domain.vote.application.dtos.VoteRequest;
import com.devtribe.domain.vote.dao.PostVoteRedisRepository;
import com.devtribe.domain.vote.entity.VoteType;
import com.devtribe.domain.vote.event.PostVoteEvent;
import com.devtribe.domain.vote.event.factory.PostDownvoteEventFactory;
import com.devtribe.domain.vote.event.factory.PostUnvoteEventFactory;
import com.devtribe.domain.vote.event.factory.PostUpvoteEventFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostVoteService {

    private final PostService postService;
    private final PostVoteRedisRepository postVoteRedisRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final PostUpvoteEventFactory postUpvoteEvent;
    private final PostDownvoteEventFactory postDownvoteEvent;
    private final PostUnvoteEventFactory postUnvoteEvent;

    public PostVoteService(
        PostService postService,
        PostVoteRedisRepository postVoteRedisRepository,
        ApplicationEventPublisher eventPublisher,
        PostUpvoteEventFactory postUpvoteEvent,
        PostDownvoteEventFactory postDownvoteEvent,
        PostUnvoteEventFactory postUnvoteEvent
    ) {
        this.postService = postService;
        this.postVoteRedisRepository = postVoteRedisRepository;
        this.eventPublisher = eventPublisher;
        this.postUpvoteEvent = postUpvoteEvent;
        this.postDownvoteEvent = postDownvoteEvent;
        this.postUnvoteEvent = postUnvoteEvent;
    }

    @Transactional(readOnly = true)
    public void postVote(Long postId, Long userId, VoteRequest voteRequest) {
        postService.getPost(postId);

        try {
            postVoteRedisRepository.vote(postId, userId, voteRequest.voteType());
            eventPublisher.publishEvent(createVoteEvent(postId, userId, voteRequest));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public void postUnvote(Long postId, Long userId) {
        postService.getPost(postId);

        try {
            postVoteRedisRepository.unvote(postId, userId);
            eventPublisher.publishEvent(postUnvoteEvent.createEvent(postId, userId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private PostVoteEvent createVoteEvent(Long postId, Long userId, VoteRequest voteRequest) {
        if (voteRequest.voteType() == VoteType.UPVOTE) {
            return postUpvoteEvent.createEvent(postId, userId);
        }
        return postDownvoteEvent.createEvent(postId, userId);
    }
}
