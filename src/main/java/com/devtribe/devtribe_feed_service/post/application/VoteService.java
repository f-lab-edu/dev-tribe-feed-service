package com.devtribe.devtribe_feed_service.post.application;

import com.devtribe.devtribe_feed_service.post.application.dtos.PostVoteResponse;
import com.devtribe.devtribe_feed_service.post.application.dtos.VoteRequest;
import com.devtribe.devtribe_feed_service.post.application.interfaces.PostRepository;
import com.devtribe.devtribe_feed_service.post.domain.Post;
import com.devtribe.devtribe_feed_service.post.domain.vote.PostVote;
import com.devtribe.devtribe_feed_service.post.domain.vote.VoteType;
import com.devtribe.devtribe_feed_service.post.repository.jpa.JpaVoteRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VoteService {

    private final JpaVoteRepository voteRepository;
    private final PostService postService;
    private final PostRepository postRepository;

    public VoteService(
        JpaVoteRepository voteRepository,
        PostService postService,
        PostRepository postRepository
    ) {
        this.voteRepository = voteRepository;
        this.postService = postService;
        this.postRepository = postRepository;
    }

    @Transactional
    public PostVoteResponse vote(VoteRequest voteRequest) {
        Post post = postService.getPost(voteRequest.postId());
        Optional<PostVote> existingVoteOpt = voteRepository.findByUserIdAndPostId(
            voteRequest.userId(), post.getId()
        );

        VoteType newType = voteRequest.voteType();

        if (existingVoteOpt.isEmpty()) {
            voteRepository.save(voteRequest.toEntity());
            applyCountDelta(post.getId(), newType);
        } else {
            PostVote existingVote = existingVoteOpt.get();
            VoteType oldType = existingVote.getVoteType();

            if (oldType != newType) {
                existingVote.updateVoteType(newType);
                voteRepository.save(existingVote);
                applyCountDelta(post.getId(), oldType);
                applyCountDelta(post.getId(), newType);
            }
        }
        return new PostVoteResponse();
    }

    private void applyCountDelta(Long postId, VoteType type) {
        if (type == VoteType.UPVOTE) {
            postRepository.upvotePost(postId);
            return;
        }

        postRepository.downvotePost(postId);
    }
    public void unvote (Long postId, Long userId){

        }
    }
