package com.devtribe.domain.post.api;

import com.devtribe.domain.post.application.PostService;
import com.devtribe.domain.post.application.dtos.CreatePostRequest;
import com.devtribe.domain.post.application.dtos.CreatePostResponse;
import com.devtribe.domain.post.application.dtos.UpdatePostRequest;
import com.devtribe.domain.post.application.dtos.UpdatePostResponse;
import com.devtribe.domain.vote.application.VoteService;
import com.devtribe.domain.vote.application.dtos.PostVoteResponse;
import com.devtribe.domain.vote.application.dtos.VoteRequest;
import com.devtribe.global.security.CustomUserDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;
    private final VoteService voteService;

    public PostController(PostService postService, VoteService voteService) {
        this.postService = postService;
        this.voteService = voteService;
    }

    @PostMapping
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody CreatePostRequest request) {
        CreatePostResponse responseBody = postService.createPost(request);
        return ResponseEntity.ok(responseBody);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdatePostResponse> updatePost(
        @PathVariable("id") Long id,
        @RequestBody UpdatePostRequest request
    ) {
        UpdatePostResponse responseBody = postService.updatePost(id, request);
        return ResponseEntity.ok(responseBody);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/vote")
    public ResponseEntity<PostVoteResponse> voteCount(
        @PathVariable("id") Long postId
    ){
        PostVoteResponse responseBody = voteService.getVoteCount(postId);
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/{id}/upvote")
    public ResponseEntity<PostVoteResponse> upvote(
        @PathVariable("id") Long postId,
        @AuthenticationPrincipal CustomUserDetail userDetail
    ) {
        Long userId = userDetail.getUserId();
        PostVoteResponse responseBody = voteService.vote(postId, VoteRequest.upvotePostRequest(userId));
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/{id}/downvote")
    public ResponseEntity<PostVoteResponse> downvote(
        @PathVariable("id") Long postId,
        @AuthenticationPrincipal CustomUserDetail userDetail
    ) {
        Long userId = userDetail.getUserId();
        PostVoteResponse responseBody = voteService.vote(postId, VoteRequest.downvotePostRequest(userId));
        return ResponseEntity.ok(responseBody);
    }

    @DeleteMapping("/{id}/unvote")
    public ResponseEntity<Void> unvote(
        @PathVariable("id") Long postId,
        @AuthenticationPrincipal CustomUserDetail userDetail
    ) {
        Long userId = userDetail.getUserId();
        voteService.unvote(postId, userId);
        return ResponseEntity.noContent().build();
    }

}
