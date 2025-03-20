package com.devtribe.devtribe_feed_service.post.api;

import com.devtribe.devtribe_feed_service.post.application.PostService;
import com.devtribe.devtribe_feed_service.post.application.dtos.CreatePostRequest;
import com.devtribe.devtribe_feed_service.post.application.dtos.CreatePostResponse;
import com.devtribe.devtribe_feed_service.post.application.dtos.UpdatePostRequest;
import com.devtribe.devtribe_feed_service.post.application.dtos.UpdatePostResponse;
import org.springframework.http.ResponseEntity;
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

    public PostController(PostService postService) {
        this.postService = postService;
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
}
