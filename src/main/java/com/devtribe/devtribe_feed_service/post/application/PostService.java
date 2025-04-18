package com.devtribe.devtribe_feed_service.post.application;

import com.devtribe.devtribe_feed_service.post.application.dtos.CreatePostRequest;
import com.devtribe.devtribe_feed_service.post.application.dtos.CreatePostResponse;
import com.devtribe.devtribe_feed_service.post.application.dtos.UpdatePostRequest;
import com.devtribe.devtribe_feed_service.post.application.dtos.UpdatePostResponse;
import com.devtribe.devtribe_feed_service.post.application.interfaces.PostRepository;
import com.devtribe.devtribe_feed_service.post.application.validators.PostRequestValidator;
import com.devtribe.devtribe_feed_service.post.domain.Post;
import com.devtribe.devtribe_feed_service.user.application.UserService;
import com.devtribe.devtribe_feed_service.user.domain.User;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private final PostRequestValidator postRequestValidator;
    private final PostRepository postRepository;
    private final UserService userService;

    public PostService(PostRequestValidator postRequestValidator,
        PostRepository postRepository,
        UserService userService) {
        this.postRequestValidator = postRequestValidator;
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public Post getPost(Long postId) {
        return postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));
    }

    @Transactional
    public CreatePostResponse createPost(CreatePostRequest request) {
        postRequestValidator.validateTitle(request.title());
        postRequestValidator.validateBody(request.content());

        User findUser = userService.getUser(request.authorId());
        Post savedPost = postRepository.save(request.toEntity(findUser));
        return new CreatePostResponse(savedPost.getId());
    }

    @Transactional
    public UpdatePostResponse updatePost(Long postId, UpdatePostRequest request) {
        postRequestValidator.validateTitle(request.title());
        postRequestValidator.validateBody(request.content());

        Post findPost = getPost(postId);
        User requestAuthor = userService.getUser(request.authorId());
        validateAuthor(findPost, requestAuthor);

        findPost.updatePostDetail(request);
        return UpdatePostResponse.from(findPost);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post findPost = getPost(postId);
        findPost.deletePost();
    }

    private void validateAuthor(Post findPost, User findAuthor) {
        Preconditions.checkArgument(findPost.isWrittenBy(findAuthor), "게시물 작성자가 아닙니다.");
    }

}
