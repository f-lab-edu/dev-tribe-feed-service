package com.devtribe.domain.vote.application;

import com.devtribe.domain.post.application.PostService;
import com.devtribe.domain.post.entity.Post;
import com.devtribe.domain.vote.application.dtos.VoteRequest;
import com.devtribe.domain.vote.dao.PostVoteRedisRepository;
import com.devtribe.domain.vote.entity.VoteType;
import com.devtribe.domain.vote.event.PostVoteEvent;
import java.time.LocalDateTime;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostVoteService {

    private final PostService postService;
    private final PostVoteRedisRepository postVoteRedisRepository;
    private final ApplicationEventPublisher eventPublisher;

    public PostVoteService(
        PostService postService,
        PostVoteRedisRepository postVoteRedisRepository,
        ApplicationEventPublisher eventPublisher
    ) {
        this.postService = postService;
        this.postVoteRedisRepository = postVoteRedisRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional(readOnly = true)
    public void postVote(Long postId, Long userId, VoteRequest voteRequest) {
        Post post = postService.getPost(postId);

        try {
            postVoteRedisRepository.vote(postId, userId, voteRequest.voteType());
            eventPublisher.publishEvent(
                new PostVoteEvent(
                    voteRequest.voteType(),
                    userId,
                    post.getId(),
                    LocalDateTime.now())
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public void postUnvote(Long postId, Long userId) {
        Post post = postService.getPost(postId);

        try {
            postVoteRedisRepository.unvote(postId, userId);
            eventPublisher.publishEvent(
                new PostVoteEvent(
                    VoteType.UNVOTE,
                    userId,
                    post.getId(),
                    LocalDateTime.now())
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
