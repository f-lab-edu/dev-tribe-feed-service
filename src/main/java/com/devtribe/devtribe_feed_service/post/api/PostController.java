package com.devtribe.devtribe_feed_service.post.api;

import static com.devtribe.devtribe_feed_service.post.application.dtos.VoteRequest.downvotePostRequest;
import static com.devtribe.devtribe_feed_service.post.application.dtos.VoteRequest.upvotePostRequest;

import com.devtribe.devtribe_feed_service.global.config.security.authentication.CustomUserDetail;
import com.devtribe.devtribe_feed_service.post.application.PostService;
import com.devtribe.devtribe_feed_service.post.application.VoteService;
import com.devtribe.devtribe_feed_service.post.application.dtos.CreatePostRequest;
import com.devtribe.devtribe_feed_service.post.application.dtos.CreatePostResponse;
import com.devtribe.devtribe_feed_service.post.application.dtos.PostVoteResponse;
import com.devtribe.devtribe_feed_service.post.application.dtos.UpdatePostRequest;
import com.devtribe.devtribe_feed_service.post.application.dtos.UpdatePostResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @PostMapping("/{id}/upvote")
    public ResponseEntity<PostVoteResponse> upvote(
        @PathVariable("id") Long postId,
        @AuthenticationPrincipal CustomUserDetail userDetail
    ) {
        Long userId = userDetail.getUserId();
        PostVoteResponse responseBody = voteService.vote(postId, upvotePostRequest(userId));
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/{id}/downvote")
    public ResponseEntity<PostVoteResponse> downvote(
        @PathVariable("id") Long postId,
        @AuthenticationPrincipal CustomUserDetail userDetail
    ) {
        Long userId = userDetail.getUserId();
        PostVoteResponse responseBody = voteService.vote(postId, downvotePostRequest(userId));
        return ResponseEntity.ok(responseBody);
    }
}
